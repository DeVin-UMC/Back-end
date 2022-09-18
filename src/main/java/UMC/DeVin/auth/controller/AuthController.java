package UMC.DeVin.auth.controller;

import UMC.DeVin.auth.IpAddressUtil;
import UMC.DeVin.auth.MemberRefreshToken;
import UMC.DeVin.auth.OAuthLoginUserUtil;
import UMC.DeVin.auth.dto.AccessTokenRes;
import UMC.DeVin.auth.repository.MemberRefreshTokenRepository;
import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.config.oauth.token.AuthToken;
import UMC.DeVin.config.oauth.token.AuthTokenProvider;
import UMC.DeVin.config.oauth.utils.CookieUtil;
import UMC.DeVin.config.properties.AppProperties;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.dto.LoginMemberRes;
import UMC.DeVin.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final MemberRepository memberRepository;
    private final OAuthLoginUserUtil oAuthLoginUserUtil;

    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";


    // 로그인 로직 (deprecated)

    /*@PostMapping("/login")
    public ApiResponse login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody AuthReq authReq
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authReq.getId(),
                        authReq.getPassword()
                )
        );

        String userId = authReq.getId();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                userId,
                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        // userId refresh token 으로 DB 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
        if (userRefreshToken == null) {
            // 없는 경우 새로 등록
            //userRefreshToken = new UserRefreshToken(userId, refreshToken.getToken(), request);
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        } else {
            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return ApiResponse.success("token", accessToken.getToken());
    }*/

    /**
     *  access token refresh 로직 (refresh token이 존재해야 합니다.)
     */
    @GetMapping("/token/refresh")
    public BaseResponse<AccessTokenRes> refreshToken (HttpServletRequest request, HttpServletResponse response) throws BaseException {


        // refresh token
        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse((null));
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);

        if (!authRefreshToken.validate()) {
            throw new BaseException(BaseResponseStatus.INVALID_REFRESH_TOKEN);
        }

        // userId refresh token 으로 DB 확인
        Optional<MemberRefreshToken> userRefreshToken = memberRefreshTokenRepository.findByRefreshToken(refreshToken);
        if (userRefreshToken.isEmpty()) {
            throw new BaseException(BaseResponseStatus.INVALID_REFRESH_TOKEN);
        }

        // 동일한 IP에서 요청했는지 확인
        if (!userRefreshToken.get().getUserIpAddress().equals(IpAddressUtil.getRemoteAddr(request))) {
            // 다른 IP에서 요청했을 경우 비정상적인 접근으로 간주, refresh token 삭제 및 로그아웃 처리
            memberRefreshTokenRepository.delete(userRefreshToken.get());
            throw new BaseException(BaseResponseStatus.LOGIN_WITH_WRONG_IP_ADDRESS);
        }

        // refresh token에 해당하는 member 조회
        Optional<Member> findMember = memberRepository.findById(userRefreshToken.get().getMemberId());

        // member가 존재하지 않을 경우
        if (findMember.isEmpty()) {
            memberRefreshTokenRepository.delete(userRefreshToken.get());
            throw new BaseException(BaseResponseStatus.INVALID_REFRESH_TOKEN);
        }

        // 새로운 access token 발급
        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                findMember.get().getEmail(),
                findMember.get().getRole().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
        if (validTime <= THREE_DAYS_MSEC) {
            // refresh 토큰 설정
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            // DB에 refresh 토큰 업데이트
            userRefreshToken.get().setRefreshToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }

        return new BaseResponse<>(new AccessTokenRes(newAccessToken.getToken()));

    }

    @GetMapping("/login/google")
    public BaseResponse<LoginMemberRes> googleLoginSuccess(@RequestParam String token) throws BaseException{
        if (token == null || !tokenProvider.convertAuthToken(token).validate()) {
            throw new BaseException(BaseResponseStatus.INVALID_ACCESS_TOKEN);
        }
        Member loginMember = oAuthLoginUserUtil.getLoginMemberWithToken(token);

        return new BaseResponse<>(new LoginMemberRes(token, loginMember.getEmail(), loginMember.getProfileImageUrl(),
                loginMember.getNickname(), loginMember.getDivision(), loginMember.getRole()));
    }
}

