package UMC.DeVin.qna.dto;

import lombok.Data;

@Data
public class PostAnswerRes {
    private Long answerId;
    public PostAnswerRes(Long id){
        this.answerId=id;
    }
}
