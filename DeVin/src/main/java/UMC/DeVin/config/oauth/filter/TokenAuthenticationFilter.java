package UMC.DeVin.config.oauth.filter;

import UMC.DeVin.auth.UserRefreshToken;
import UMC.DeVin.auth.repository.UserRefreshTokenRepository;
import UMC.DeVin.config.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import UMC.DeVin.config.oauth.token.AuthTokenProvider;
import UMC.DeVin.config.oauth.token.AuthToken;
import UMC.DeVin.config.oauth.utils.CookieUtil;
import UMC.DeVin.config.oauth.utils.HeaderUtil;
import UMC.DeVin.config.properties.AppProperties;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.repository.MemberRepository;
import UMC.DeVin.member.role.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthTokenProvider tokenProvider;
    /*private final UserRefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final AppProperties appProperties;*/

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)  throws ServletException, IOException {

        String tokenStr = HeaderUtil.getAccessToken(request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);

        if (token.validate()) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        /*// access token 만료 시
        else {
            Optional<Cookie> refreshToken = CookieUtil.getCookie(request, OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN);
            if (refreshToken.isPresent()) {
                // 쿠키에 refresh token이 존재할 경우
                Optional<UserRefreshToken> findRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken.get().getValue());

                // DB에 refresh token이 존재할 경우
                if (findRefreshToken.isPresent()) {
                    AuthToken refreshAuthToken = tokenProvider.convertAuthToken(findRefreshToken.get().getRefreshToken());

                    // refresh token이 유효할 경우
                    if (refreshAuthToken.validate()) {

                        Optional<Member> findMember = memberRepository.findById(findRefreshToken.get().getMemberId());

                        if (findMember.isPresent()) {
                            Date now = new Date();
                            AuthToken newAccessToken = tokenProvider.createAuthToken(
                                    findRefreshToken.get().getUserId(),
                                    findMember.get().getRole().getCode(),
                                    new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
                            );
                        }



                    }
                }

            }
        }*/

        filterChain.doFilter(request, response);
    }
}