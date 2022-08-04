package UMC.DeVin.qna.dto;

import lombok.Data;

@Data
public class PostQuestionRes {
    private Long postIdx;

    public PostQuestionRes(Long postIdx){
        this.postIdx = postIdx;
    }
}
