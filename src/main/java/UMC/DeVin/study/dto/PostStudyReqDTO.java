package UMC.DeVin.study.dto;

import UMC.DeVin.common.Level;
import UMC.DeVin.project.dto.RegionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 스터디 엔티티 생성 관련 DTO입니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostStudyReqDTO {
    private String title;
    private String description;
    private Level level;
    private Integer recruitNumber;
    private List<RegionDto> regionDtos;
}
