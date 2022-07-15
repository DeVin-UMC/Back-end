package UMC.DeVin.project.entity;

import UMC.DeVin.project.dto.RegionDto;
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

    @Column(name = "rec_title")
    private String title;

    public ProjectRegion(RegionDto dto){
        this.title = dto.getTitle();
    }

    public void setProject(Project project){
        this.project=project;
    }

    public static ProjectRegion createRegion(Project project, String title) {
        ProjectRegion newRegion = new ProjectRegion();
        newRegion.project = project;
        newRegion.title = title;

        return newRegion;
    }

}
