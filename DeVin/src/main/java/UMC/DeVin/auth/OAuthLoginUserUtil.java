package UMC.DeVin.auth;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;


/**
 *  OAuth 의 로그인된 사용자의 정보를 가져오는 Util 클래스
 */

@Component
@RequiredArgsConstructor
public class OAuthLoginUserUtil {

    private final MemberService memberService;

    public Member getLoginMember() throws BaseException {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Member member = memberService.getUser(principal.getUsername());

        // Security context에 로그인된 사용자가 없을 경우
        if (member == null) {
            throw new BaseException(BaseResponseStatus.NO_LOGIN_USER);
        }

        return member;
    }

}
