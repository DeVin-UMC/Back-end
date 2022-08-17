package UMC.DeVin.project;

import UMC.DeVin.auth.OAuthLoginUserUtil;
import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.role.MemberRole;
import UMC.DeVin.project.dto.ProjectRes;
import UMC.DeVin.project.dto.PostProjectReqDto;
import UMC.DeVin.project.dto.PostProjectResDto;
import UMC.DeVin.project.dto.ProjectSearchCondition;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final OAuthLoginUserUtil oAuthLoginUserUtil;

    /**
     * 프로젝트 생성 API
     * [POST] /projects
     * @return BaseResponse<PostProjectResDto>
     */
    @PostMapping("/projects")
    public BaseResponse<PostProjectResDto> createProject(@RequestBody @Valid PostProjectReqDto dto) throws BaseException {

        // 로그인 유저
        Member loginMember = oAuthLoginUserUtil.getLoginMemberWithContext();

        // 게스트가 글쓰기에 접근한 경우
        if (loginMember.getRole().equals(MemberRole.GUEST)) {
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
        }

        PostProjectResDto postProjectResDto = projectService.createProject(dto, loginMember);
        return new BaseResponse<>(postProjectResDto);

    }


    /**
     * 페이징 API
     *
     * [Get] /projects?platform=web&region=seoul&level=beginner&page=0&size=10
     * - page : 현재 페이지 (default 0)
     * - size : 조회할 데이터 수 (default 10)
     * @return Page<GetProjectDto>
     */
    @GetMapping("/projects")
    public BaseResponse<Page<ProjectRes>> findPage(ProjectSearchCondition condition, @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable){
        return new BaseResponse<>(projectService.findPage(condition,pageable));
    }


    /**
     * 검색 API
     * [Get] /projects/search?keyword=검색어
     * @return Page<GetProjectDto>
     */
    @GetMapping("/projects/search")
    public BaseResponse<Page<ProjectRes>> search(@RequestParam String keyword, @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable){
        // 검색어 2글자 이상 입력
        if(keyword.length()<2){
            return new BaseResponse<>(BaseResponseStatus.REQUEST_KEYWORD);
        }
        return new BaseResponse<>(projectService.search(keyword,pageable));
    }

}

