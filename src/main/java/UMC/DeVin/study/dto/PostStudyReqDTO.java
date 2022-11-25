package UMC.DeVin.study.dto;

import UMC.DeVin.common.Level;
import UMC.DeVin.project.dto.RegionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotBlank;

import java.util.List;

/**
 * 스터디 엔티티 생성 관련 DTO입니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostStudyReqDTO {

    @NotBlank(message = "스터디 제목을 입력해주세요 !")
    private String title;
    @NotBlank(message = "스터디 설명을 입력해주세요 !")
    private String description;
    private Level level;
    private Integer recruitNumber;
    private MultipartFile file;
    private List<RegionDTO> regionDto;
}
