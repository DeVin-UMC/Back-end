package UMC.DeVin.project.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "PROJECT_REGION")
public class ProjectRegion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reg_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pro_id")
    private Project project;

    @Column(name = "rec_title", nullable = false)
    private String title;

    @Builder
    public ProjectRegion(Long id, Project project, String title){
        this.id = id;
        this.project = project;
        this.title = title;
    }

}
