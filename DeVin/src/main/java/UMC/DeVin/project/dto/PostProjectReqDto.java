package UMC.DeVin.project.dto;


import UMC.DeVin.project.entity.Project;
import UMC.DeVin.project.entity.ProjectPlatform;
import UMC.DeVin.project.entity.ProjectRecruitment;
import UMC.DeVin.project.entity.ProjectRegion;
import UMC.DeVin.project.entity.level.ProgramLevel;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class PostProjectReqDto {

//    private Member member;
    private String title;
    private String des;
    private ProgramLevel programLevel;
    private String img;
    private List<ProjectPlatform> Platforms;
    private List<ProjectRecruitment> Recruitments;
    private List<ProjectRegion> Regions;

    /* Dto -> Entity */
    public Project toEntity(){
        return Project.builder()
                .title(title)
                .des(des)
                .programLevel(programLevel)
                .img(img)
                .projectPlatforms(Platforms)
                .projectRecruitments(Recruitments)
                .projectRegions(Regions)
                .build();
    }

}
