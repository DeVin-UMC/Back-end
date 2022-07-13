package UMC.DeVin.member.controller;

import UMC.DeVin.member.Member;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/test")
    public String loginTest() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();
    }

    @GetMapping
    public BaseResponse<MemberRes> getUser() throws BaseException {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Member member = memberService.getUser(principal.getUsername());

        if (member == null) {
            throw new BaseException(BaseResponseStatus.NO_LOGIN_USER);
        }

        return new BaseResponse<>(new MemberRes(member.getEmail(), member.getProfileImageUrl(), member.getNickname(),
                member.getDivision(), member.getRole()));

    }
}
