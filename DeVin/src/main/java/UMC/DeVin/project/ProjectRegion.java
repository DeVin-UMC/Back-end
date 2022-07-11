package UMC.DeVin.project;

import UMC.common.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "PROJECT_REGION")
public class ProjectRegion {

    @Id @GeneratedValue
    @Column(name = "reg_id")
    private Long id;

    @OneToOne
    @JoinColumn(name="pro_id", nullable = false)
    private Project project;

    @Column(name = "rec_title", nullable = false)
    private String title;

    @Builder
    public ProjectRegion(Project project, String title){
        this.project = project;
        this.title = title;
    }

}
