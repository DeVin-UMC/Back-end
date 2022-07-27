package UMC.DeVin.project.dto;

import UMC.DeVin.project.entity.Project;
import lombok.*;

@Getter
public class PostProjectResDto {

    private Long id;
    public PostProjectResDto(Project entity){
        this.id= entity.getId();
    }
}
