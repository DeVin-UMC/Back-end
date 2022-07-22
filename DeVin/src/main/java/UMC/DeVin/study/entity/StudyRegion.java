package UMC.DeVin.study.entity;

import UMC.DeVin.common.base.BaseEntity;
import UMC.DeVin.qna.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

//@ManyToOne(fetch = LAZY)
//@JoinColumn(name = "pro_id")
//private Project project;

@Entity
@Getter
@Table(name = "STUDY_REGION")
public class StudyRegion extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "reg_id", nullable = false)
    private int id;

    @ManyToOne
    @Column(name = "std_id", nullable = false)
    private Study study;

    @Column(name = "reg_title", nullable = false)
    private String title;

    protected StudyRegion(){}
}
