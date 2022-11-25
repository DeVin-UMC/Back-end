package UMC.DeVin.study.dto;

import UMC.DeVin.common.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDTO {
    @NotBlank(message = "원하시는 지역을 입력해주세요 !")
    private Region region;
}
