package UMC.DeVin.qna.dto;

import lombok.Data;

@Data
public class PostQuestionRes {
    private Long questionId;

    public PostQuestionRes(Long questionId){
        this.questionId = questionId;
    }
}
