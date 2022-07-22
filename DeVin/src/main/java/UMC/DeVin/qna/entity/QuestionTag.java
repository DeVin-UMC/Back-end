package UMC.DeVin.qna.entity;

import UMC.DeVin.common.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "QUESTION_TAG")
public class QuestionTag extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "tag_id", nullable = false)
    private int id;

    @ManyToOne
    @Column(name = "qus_id", nullable = false)
    private Question question;

    @Column(name = "tag_title", nullable = false)
    private String title;

    protected QuestionTag(){}
}
