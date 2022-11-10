package UMC.DeVin.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostQuestionReq {
    private String title;
    private String content;
    private List<PostTagReq> tagList;
}
