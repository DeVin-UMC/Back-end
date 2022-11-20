package UMC.DeVin.project.dto;

import UMC.DeVin.common.Level;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostProjectReqDto {

    @NotBlank(message = "프로젝트 제목을 입력해주세요 !")
    private String title;
    @NotBlank(message = "프로젝트 설명을 입력해주세요 !")
    private String des;
    private Level programLevel;

    private List<PlatformDto> platformList;
    private List<RecruitmentDto> recruitmentList;
    private List<RegionDto> regionList;

}
