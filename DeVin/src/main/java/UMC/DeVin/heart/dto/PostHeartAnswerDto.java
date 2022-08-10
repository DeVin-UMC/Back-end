package UMC.DeVin.heart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostHeartAnswerDto {
    private Long memberId;
    private Long answerId;
}
