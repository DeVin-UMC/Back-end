package UMC.DeVin.project.entity;

import UMC.DeVin.project.dto.PlatformDto;
import UMC.DeVin.project.dto.PostProjectReqDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "PROJECT_PLATFORM")
public class ProjectPlatform {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pla_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pro_id")
    private Project project;

    @Column(name = "pla_title")
    private String title;

    public ProjectPlatform(PlatformDto dto){
        this.title = dto.getTitle();
    }

    public void setProject(Project project){
        this.project=project;
    }

    public static ProjectPlatform createProjectPlatform(Project project, String title) {
        ProjectPlatform newPlatform = new ProjectPlatform();
        newPlatform.project = project;
        newPlatform.title = title;

        return newPlatform;
    }

}