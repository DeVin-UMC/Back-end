package UMC.DeVin.project.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class RegionDto {

    @NotBlank(message = "원하시는 지역을 입력해주세요 !")
    private String title;
}
