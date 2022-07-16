package UMC.DeVin.project.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
public class PlatformDto {
    @NotNull(message = "플랫폼을 선택해주세요 !")
    private String title;
}
