package UMC.DeVin.qna.entity;

import UMC.DeVin.common.base.BaseEntity;
import UMC.DeVin.qna.dto.PostQuestionReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "QUESTION")
public class Question extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qus_id")
    private Long id;

    @Column(name = "qus_title", nullable = false)
    private String title;

    @Column(name = "qus_cont", nullable = false)
    private String content;


    public static Question createQuestion(PostQuestionReq dto){
        Question question = new Question();
        question.title = dto.getTitle();
        question.content = dto.getContent();
        return question;
    }

}