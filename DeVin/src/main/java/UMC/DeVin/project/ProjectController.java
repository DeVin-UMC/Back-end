package UMC.DeVin.project;

import UMC.DeVin.auth.OAuthLoginUserUtil;
import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.repository.MemberRepository;
import UMC.DeVin.member.role.MemberRole;
import UMC.DeVin.project.dto.PostProjectReqDto;
import UMC.DeVin.project.dto.PostProjectResDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final OAuthLoginUserUtil oAuthLoginUserUtil;

    private final MemberRepository memberRepository;

    /**
     * 프로젝트 생성 API
     * [POST] /projects
     * @return BaseResponse<PostProjectResDto>
     */
    @PostMapping("/projects")
    public BaseResponse<PostProjectResDto> createProject(@RequestBody @Valid PostProjectReqDto dto) throws BaseException {

        // 로그인된 유저
        Member loginMember = oAuthLoginUserUtil.getLoginMemberWithContext();
        // 작성자
        Member findMember = memberRepository.findById(dto.getMemberId()).get();

        // 로그인된 유저와 작성자가 다른 경우
        if(loginMember != findMember){
            return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
        }

        // 게스트가 글쓰기에 접근한 경우
        if(findMember.getRole().equals(MemberRole.GUEST) || loginMember.getRole().equals(MemberRole.GUEST)){
            return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
        }

        PostProjectResDto postProjectResDto = projectService.createProject(dto, findMember);
        return new BaseResponse<>(postProjectResDto);

    }
}
