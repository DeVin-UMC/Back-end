package UMC.DeVin.study.dto;

import UMC.DeVin.common.Level;
import UMC.DeVin.member.dto.MemberRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 스터디 엔티티를 response에 실어서 보낼 때 사용하는 DTO입니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyResDTO {
    private Long id;
    private String title;
    private String description;
    private Level level;
    private int recruitNumber;
    private String imageUrl;
    private MemberRes memberRes;
    private List<RegionDTO> regions;
}
