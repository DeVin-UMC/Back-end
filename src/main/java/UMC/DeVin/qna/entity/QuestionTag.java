package UMC.DeVin.qna.entity;

import UMC.DeVin.common.base.BaseEntity;
import lombok.Getter;
import javax.persistence.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "QUESTION_TAG")
public class QuestionTag extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "qus_id",nullable = false)
    private Question question;

    @Column(name = "tag_title", nullable = false)
    private String title;

    protected QuestionTag() { }

    public static QuestionTag createTag(Question question, String title){
        QuestionTag tag = new QuestionTag();
        tag.question = question;
        tag.title = title;
        return tag;
    }

}
