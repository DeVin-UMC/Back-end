package UMC.DeVin.project.dto;

import UMC.DeVin.project.entity.Project;
import UMC.DeVin.project.entity.ProjectPlatform;
import UMC.DeVin.project.entity.ProjectRecruitment;
import UMC.DeVin.project.entity.ProjectRegion;
import UMC.DeVin.project.entity.level.ProgramLevel;
import lombok.*;

@Getter
public class PostProjectResDto {

    public Long id;
    public PostProjectResDto(Project entity){
        this.id= entity.getId();
    }
}
