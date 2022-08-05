package UMC.DeVin.qna.entity;

import UMC.DeVin.common.base.BaseEntity;
import UMC.DeVin.qna.dto.PostAnswerReq;
import UMC.DeVin.qna.entity.select.Select;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "ANSWER")
public class Answer extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ans_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "qus_id")
    private Question question;

    @Column(name = "qus_content", nullable = false)
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ans_is_selected", nullable = false)
    private Select select ;

    public static Answer createAnswer(PostAnswerReq dto, Question question){
        Answer answer = new Answer();
        answer.question = question;
        answer.content = dto.getContent();
        answer.select = Select.FALSE;
        return answer;
    }

    public void selectAnswer(){
        this.select = Select.TRUE;
    }

}
