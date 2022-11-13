package UMC.DeVin.member.controller;

import UMC.DeVin.auth.IpAddressUtil;
import UMC.DeVin.auth.MemberRefreshToken;
import UMC.DeVin.auth.OAuthLoginUserUtil;
import UMC.DeVin.auth.repository.MemberRefreshTokenRepository;
import UMC.DeVin.config.oauth.token.AuthTokenProvider;
import UMC.DeVin.config.oauth.utils.CookieUtil;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.dto.MemberJoinRes;
import UMC.DeVin.member.dto.MemberRes;
import UMC.DeVin.member.service.MemberService;
import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.common.base.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static UMC.DeVin.config.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final OAuthLoginUserUtil oAuthLoginUserUtil;
    private final AuthTokenProvider tokenProvider;
    private final MemberRefreshTokenRepository refreshTokenRepository;

    /**
     *  로그인된 사용자 반환 테스트용
     */
    @GetMapping("/test/user")
    public BaseResponse<MemberRes> getUser() throws BaseException {

        Member member = oAuthLoginUserUtil.getLoginMemberWithContext();

        return new BaseResponse<>(new MemberRes(member.getId(), member.getEmail(), member.getProfileImageUrl(), member.getNickname(),
                member.getDivision(), member.getRole()));

    }

    /**
     *  OAuth 를 통한 로그인 이후, 첫 로그인일 경우 (회원가입) 시 추가 정보 입력을 위해 사용
     */
    @GetMapping("/join")
    public BaseResponse<MemberJoinRes> joinMember(@RequestParam String token, HttpServletRequest request,
                                                  HttpServletResponse response) throws BaseException {

        // access token이 유효하지 않을 경우 (비정상적인 접근)
        if (!tokenProvider.convertAuthToken(token).validate()) {
            throw new BaseException(BaseResponseStatus.INVALID_ACCESS_TOKEN);
        }

        // access token으로 해당 User 탐색
        User user = (User) tokenProvider.getAuthentication(tokenProvider.convertAuthToken(token)).getPrincipal();

        Member loginMember = memberService.getMember(user.getUsername());

        // 이미 존재하는 회원일 경우
        if (loginMember.getNickname() != null) {
            // refresh token 삭제
            Optional<MemberRefreshToken> findToken =
                    refreshTokenRepository.findByMemberIdAndUserIpAddress(loginMember.getId(), IpAddressUtil.getRemoteAddr(request));

            findToken.ifPresent(refreshTokenRepository::delete);

            // 쿠키에서 refresh token 삭제
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);

            throw new BaseException(BaseResponseStatus.ALREADY_JOIN_BEFORE);
        }

        return new BaseResponse<>(new MemberJoinRes(loginMember.getEmail(), loginMember.getProfileImageUrl(),
                loginMember.getDivision(), loginMember.getRole(), token));

    }
}
