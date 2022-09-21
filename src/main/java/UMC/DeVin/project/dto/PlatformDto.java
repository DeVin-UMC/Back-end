package UMC.DeVin.project.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformDto {
    @NotBlank(message = "플랫폼을 선택해주세요 !")
    private String title;
}
