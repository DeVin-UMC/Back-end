package UMC.DeVin.qna.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetQnaDto {
    private String title;
    private String content;
    private int countAnswer;
    private int countLike;
    private int countUnlike;
    private List<String> tags;

    /* 로그인 한 경우 */
    // 추천 했으면 true
    private boolean like;
    // 비추천 했으면 true
    private boolean unlike;
    // 답변을 달았다면 true
    private boolean comment;
}
