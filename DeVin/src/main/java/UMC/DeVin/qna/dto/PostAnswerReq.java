package UMC.DeVin.qna.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostAnswerReq {
    private Long questionId;
    private String content;
}
