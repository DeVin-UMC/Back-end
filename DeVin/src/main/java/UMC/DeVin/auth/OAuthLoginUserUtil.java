package UMC.DeVin.auth;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.config.oauth.token.AuthTokenProvider;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;


/**
 *  OAuth 의 로그인된 사용자의 정보를 가져오는 Util 클래스
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthLoginUserUtil {

    private final MemberService memberService;
    private final AuthTokenProvider tokenProvider;

    public Member getLoginMemberWithContext() throws BaseException {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Member member = memberService.getMember(principal.getUsername());

        // Security context에 로그인된 사용자가 없을 경우
        if (member == null) {
            throw new BaseException(BaseResponseStatus.NO_LOGIN_USER);
        }

        return member;
    }

    public Member getLoginMemberWithToken(String token) throws BaseException {
        // access token으로 해당 User 탐색
        User user = (User) tokenProvider.getAuthentication(tokenProvider.convertAuthToken(token)).getPrincipal();

        Member loginMember = memberService.getMember(user.getUsername());

        if (loginMember == null) {
            throw new BaseException(BaseResponseStatus.INVALID_ACCESS_TOKEN);
        }

        return loginMember;
    }

}
