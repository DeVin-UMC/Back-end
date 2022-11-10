package UMC.DeVin.qna.entity;

import UMC.DeVin.common.base.BaseEntity;
import UMC.DeVin.member.Member;
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
    @Column(name = "ans_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "qus_id",nullable = false)
    private Question question;

    @Column(name = "ans_cont", nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "ans_writer", nullable = false)
    private Member writer;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ans_is_selected", nullable = false)
    private Select select ;

    public static Answer createAnswer(PostAnswerReq dto, Question question, Member member){
        Answer answer = new Answer();
        answer.question = question;
        answer.content = dto.getContent();
        answer.select = Select.FALSE;
        answer.writer = member;
        return answer;
    }

    public void selectAnswer(){
        this.select = Select.TRUE;
    }
    public void unselectAnswer(){
        this.select = Select.FALSE;
    }

}
