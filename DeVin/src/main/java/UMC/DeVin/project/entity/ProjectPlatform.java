package UMC.DeVin.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "PROJECT_PLATFORM")
public class ProjectPlatform {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pla_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pro_id")
    @JsonIgnore
    private Project project;

    @Column(name = "pla_title")
    private String title;

    protected ProjectPlatform(){ }

    /* 플랫폼 생성 */
    public static ProjectPlatform createProjectPlatform(Project project, String title) {
        ProjectPlatform newPlatform = new ProjectPlatform();
        newPlatform.project = project;
        newPlatform.title = title;

        return newPlatform;
    }

}