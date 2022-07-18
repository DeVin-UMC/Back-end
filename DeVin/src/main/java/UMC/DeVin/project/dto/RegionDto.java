package UMC.DeVin.project.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
public class RegionDto {

    @NotNull(message = "원하시는 지역을 입력해주세요 !")
    private String title;
}
