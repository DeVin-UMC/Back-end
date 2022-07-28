package UMC.DeVin.qna.entity;

import UMC.DeVin.common.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@Getter
@Table(name = "QUESTION")

public class Question extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "qus_id", nullable = false)
    private int id;

    @Column(name = "qus_title", nullable = false)
    private String title;

    @Column(name = "qus_cont", nullable = false)
    private String content;


}