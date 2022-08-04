package UMC.DeVin.study.entity;

import UMC.DeVin.common.base.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "STUDY_REGION")
public class StudyRegion extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "reg_id", nullable = false)
    private int id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "std_id")
    private Study study;

    @Column(name = "reg_title", nullable = false)
    private String title;

    protected StudyRegion(){}
}
