package UMC.DeVin.project;


import UMC.DeVin.project.dto.PostProjectReqDto;
import UMC.DeVin.project.dto.PostProjectResDto;
import UMC.common.base.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /* 게시글 생성 */
    @PostMapping("api/posts/project")
    public BaseResponse<PostProjectResDto> createPost(@RequestBody PostProjectReqDto dto){
        PostProjectResDto postProjectResDto = projectService.createProject(dto);
        return new BaseResponse<>(postProjectResDto);

    }
}
