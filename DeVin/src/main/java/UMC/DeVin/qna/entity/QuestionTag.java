package UMC.DeVin.qna.entity;

import UMC.DeVin.common.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

<<<<<<< Updated upstream
=======
import static javax.persistence.FetchType.LAZY;

>>>>>>> Stashed changes
@Entity
@Getter
@Table(name = "QUESTION_TAG")
public class QuestionTag extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "tag_id", nullable = false)
    private int id;

<<<<<<< Updated upstream
    @ManyToOne
    @Column(name = "qus_id", nullable = false)
=======
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "qus_id")
>>>>>>> Stashed changes
    private Question question;

    @Column(name = "tag_title", nullable = false)
    private String title;

    protected QuestionTag(){}
}
