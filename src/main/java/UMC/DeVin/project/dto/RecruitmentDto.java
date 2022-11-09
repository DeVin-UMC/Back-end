package UMC.DeVin.project.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentDto {

    @NotBlank(message = "모집 분야를 선택해주세요 !")
    private String title;
    @NotBlank(message = "사용 언어를 입력해주세요 !")
    private String language;
    @NotBlank(message = "해당 분야의 모집 인원을 입력해주세요 !")
    private Integer num;
}
