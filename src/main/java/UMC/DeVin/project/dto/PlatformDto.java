package UMC.DeVin.project.dto;

import UMC.DeVin.common.Platform;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformDto {
    @NotBlank(message = "플랫폼을 선택해주세요 !")
    private Platform title;
}
