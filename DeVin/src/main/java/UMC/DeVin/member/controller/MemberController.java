package UMC.DeVin.member.controller;

import UMC.DeVin.auth.OAuthLoginUserUtil;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.dto.MemberJoinRes;
import UMC.DeVin.member.dto.MemberRes;
import UMC.DeVin.member.service.MemberService;
import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.common.base.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final OAuthLoginUserUtil oAuthLoginUserUtil;

    /**
     *  로그인된 사용자 반환 테스트용
     */
    @GetMapping("/test/user")
    public BaseResponse<MemberRes> getUser() throws BaseException {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Member member = memberService.getUser(principal.getUsername());

        if (member == null) {
            throw new BaseException(BaseResponseStatus.NO_LOGIN_USER);
        }

        return new BaseResponse<>(new MemberRes(member.getEmail(), member.getProfileImageUrl(), member.getNickname(),
                member.getDivision(), member.getRole()));

    }

    /**
     *  OAuth 를 통한 로그인 이후, 첫 로그인일 경우 (회원가입) 시 추가 정보 입력을 위해 사용
     */
    @GetMapping("/join")
    public BaseResponse<MemberJoinRes> joinMember() throws BaseException {

        Member loginMember = oAuthLoginUserUtil.getLoginMember();

        return new BaseResponse<>(new MemberJoinRes(loginMember.getEmail(), loginMember.getProfileImageUrl(),
                loginMember.getDivision(), loginMember.getRole()));

    }
}
