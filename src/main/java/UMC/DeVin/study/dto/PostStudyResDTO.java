package UMC.DeVin.study.dto;


import UMC.DeVin.study.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 프로젝트 생성시 반환되는 DTO입니다.
 */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Getter
public class PostStudyResDTO {
    private Long id;
    public PostStudyResDTO(Study entity){
        this.id = entity.getId();
    }
}
