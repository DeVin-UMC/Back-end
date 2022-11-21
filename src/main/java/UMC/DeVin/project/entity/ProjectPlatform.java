package UMC.DeVin.project.entity;

import UMC.DeVin.common.Platform;
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
    @JoinColumn(name = "pro_id",nullable = false)
    @JsonIgnore
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "pla_title",nullable = false)
    private Platform title;

    protected ProjectPlatform(){ }

    /* 플랫폼 생성 */
    public static ProjectPlatform createProjectPlatform(Project project, Platform title) {
        ProjectPlatform newPlatform = new ProjectPlatform();
        newPlatform.project = project;
        newPlatform.title = title;

        return newPlatform;
    }

}