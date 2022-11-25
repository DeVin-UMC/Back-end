package UMC.DeVin.study.entity;

import UMC.DeVin.common.Region;
import UMC.DeVin.common.base.BaseEntity;
import UMC.DeVin.study.dto.RegionDTO;
import lombok.Getter;
import javax.persistence.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "STUDY_REGION")
public class StudyRegion extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reg_id", nullable = false)
    private long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "std_id", nullable = false)
    private Study study;

    @Enumerated(EnumType.STRING)
    @Column(name = "reg_title", nullable = false)
    private Region region;

    protected StudyRegion() { }

    private StudyRegion(Study study, Region region) {
        this.study = study;
        this.region = region;
    }

    /**
     * 특정 스터디의 지역 정보를 저장하는 StudyRegion 엔티티를 생성합니다.
     * 해당 함수로만 StudyRegion 클래스를 생성할 수 있습니다.
     * @param study 해당하는 스터디 엔티티
     * @param dto 지역을 담고 있는 DTO
     * @return 생성된 스터디-지역 엔티티
     */
    public static StudyRegion createStudyRegion(Study study, RegionDTO dto) {
        return new StudyRegion(study, dto.getRegion());
    }
}
