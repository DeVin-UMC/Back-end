package UMC.DeVin.project.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitmentDto {

    @NotNull(message = "모집 분야를 선택해주세요 !")
    private String title;
    @NotNull(message = "해당 분야의 모집 인원을 입력해주세요 !")
    private Integer num;
}
