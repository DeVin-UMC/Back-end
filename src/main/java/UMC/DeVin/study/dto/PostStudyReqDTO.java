package UMC.DeVin.study.dto;

import UMC.DeVin.study.entity.StudyLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 스터디 엔티티 생성 관련 DTO입니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostStudyReqDTO {
    private String title;
    private String description;
    private StudyLevel level;
    private Integer recruitNumber;
}
