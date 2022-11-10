package UMC.DeVin.qna.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostQuestionReq {
    @Schema(example="질문 제목입니다.", required=true)
    private String title;

    @Schema(example="질문 내용입니다.", required=true)
    private String content;

    @Schema(example="질문 태그입니다.", required=true)
    private List<PostTagReq> tagList;
}
