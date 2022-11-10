package UMC.DeVin.qna.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostAnswerReq {
    @Schema(example="질문 id 값입니다.", required=true)
    private Long questionId;

    @Schema(example="답변 내용입니다.", required=true)
    private String content;
}
