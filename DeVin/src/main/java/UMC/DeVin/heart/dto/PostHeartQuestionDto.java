package UMC.DeVin.heart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostHeartQuestionDto {
    private Long memberId;
    private Long questionId;
}