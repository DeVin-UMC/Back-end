package UMC.DeVin.config.security;

import UMC.DeVin.auth.OAuthLoginUserUtil;
import UMC.DeVin.auth.repository.MemberRefreshTokenRepository;
import UMC.DeVin.config.oauth.handler.TokenAccessDeniedHandler;
import UMC.DeVin.config.oauth.exception.RestAuthenticationEntryPoint;
import UMC.DeVin.config.oauth.filter.TokenAuthenticationFilter;
import UMC.DeVin.config.oauth.handler.OAuth2AuthenticationFailureHandler;
import UMC.DeVin.config.oauth.handler.OAuth2AuthenticationSuccessHandler;
import UMC.DeVin.config.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import UMC.DeVin.config.oauth.service.CustomOAuth2UserService;
import UMC.DeVin.config.oauth.service.CustomUserDetailsService;
import UMC.DeVin.config.oauth.token.AuthTokenProvider;
import UMC.DeVin.config.properties.AppProperties;
import UMC.DeVin.config.properties.CorsProperties;
import UMC.DeVin.member.repository.MemberRepository;
import UMC.DeVin.member.role.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final MemberRepository memberRepository;
    private final OAuthLoginUserUtil oAuthLoginUserUtil;
    private final MemberRefreshTokenRepository refreshTokenRepository;

    /*
     * UserDetailsService 설정
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint(oAuthLoginUserUtil, refreshTokenRepository, memberRepository))
                .accessDeniedHandler(tokenAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/api/**").hasAnyAuthority(MemberRole.USER.getCode())
                .antMatchers("/api/**/admin/**").hasAnyAuthority(MemberRole.ADMIN.getCode())
                .antMatchers("/login/google").permitAll()
                .antMatchers("/token/refresh").permitAll()
                .antMatchers("/join").permitAll()
                .antMatchers("/test").permitAll()
                .antMatchers("/questions/**").permitAll()


                // 모든 기능 개발 완료될 때까지 Spring Security 적용하지 않음
                .antMatchers("/projects/**").permitAll()
                .antMatchers("/project/**").permitAll()
                .antMatchers("/study/**").permitAll()
                .antMatchers("/test/**").permitAll()
                .antMatchers("/questions/**").permitAll()
                .antMatchers("/answers/**").permitAll()
                .antMatchers("/qna/**").permitAll()
                // 모든 기능 개발 완료될 때까지 Spring Security 적용하지 않음


                // swagger 관련 처리
                .antMatchers("/swagger/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/api-docs/**").permitAll()
                // swagger 관련 처리


                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/*/oauth2/code/*")
                .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler());

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /*
     * auth 매니저 설정
     * */
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /*
     * security 설정 시, 사용할 인코더 설정
     * */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * 토큰 필터 설정
     * */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    /*
     * 쿠키 기반 인가 Repository
     * 인가 응답을 연계 하고 검증할 때 사용.
     * */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    /*
     * Oauth 인증 성공 핸들러
     * */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                memberRefreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                memberRepository
        );
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }

    /*
     * Cors 설정
     * */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }

    /**
     *  정적 리소스 Spring Security 필터로부터 ignore
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/favicon.ico");
        web.ignoring().mvcMatchers("/error");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


}
