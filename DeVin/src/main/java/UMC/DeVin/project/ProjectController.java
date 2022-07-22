package UMC.DeVin.project;

import UMC.DeVin.auth.OAuthLoginUserUtil;
import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.repository.MemberRepository;
import UMC.DeVin.member.role.MemberRole;
import UMC.DeVin.project.dto.GetProjectDto;
import UMC.DeVin.project.dto.PostProjectReqDto;
import UMC.DeVin.project.dto.PostProjectResDto;
import UMC.DeVin.project.entity.Project;
import UMC.DeVin.project.repository.ProjectPlatformRepository;
import UMC.DeVin.project.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

        // 로그인 유저
        Member loginMember = oAuthLoginUserUtil.getLoginMemberWithContext();

        // 작성자
        Optional<Member> optionalMember = memberRepository.findById(dto.getMemberId());
        Member findMember = optionalMember.orElse(null);

        // 존재하지 않는 멤버의 id 값을 넘겨준 경우
        if(findMember == null){
            return new BaseResponse<>(BaseResponseStatus.USERS_EMPTY_USER_ID);
        }
        // 로그인된 유저와 작성자가 다른 경우
        if (!loginMember.getId().equals(dto.getMemberId())) {
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
        }
        // 게스트가 글쓰기에 접근한 경우
        if (findMember.getRole().equals(MemberRole.GUEST) || loginMember.getRole().equals(MemberRole.GUEST)) {
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
        }

        PostProjectResDto postProjectResDto = projectService.createProject(dto, findMember);
        return new BaseResponse<>(postProjectResDto);

    }


    /**
     * 프로젝트 업로드 날짜 기준 N개씩 페이징 API
     *
     * [Get] /projects?page=0&size=10
     * - page : 현재 페이지 (default 0)
     * - size : 조회할 데이터 수 (default 10)
     * @return List<GetProjectDto>
     */
    @GetMapping("/projects")
    public List<GetProjectDto> findAll(@PageableDefault(direction = Sort.Direction.DESC) Pageable pageable){

        return projectService.findAll(pageable);
    }

}

