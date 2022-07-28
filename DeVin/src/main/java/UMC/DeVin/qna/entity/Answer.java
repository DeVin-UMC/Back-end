package UMC.DeVin.qna.entity;

import UMC.DeVin.common.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;


@Entity
@NoArgsConstructor
@Getter
@Table(name = "ANSWER")

public class Answer extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ans_id", nullable = false)
    private int id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "qus_id")
    private Question question;

    @Column(name = "qus_content", nullable = false)
    private String content;


    @Column(name = "ans_is_selected", nullable = false)
    private String select ;


}
