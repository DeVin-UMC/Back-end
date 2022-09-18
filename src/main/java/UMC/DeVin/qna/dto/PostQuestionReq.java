package UMC.DeVin.qna.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostQuestionReq {
    private String title;
    private String content;
    private List<PostTagReq> tagList;
}
