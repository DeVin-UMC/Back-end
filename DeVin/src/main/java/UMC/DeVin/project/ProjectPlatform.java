package UMC.DeVin.project;

import UMC.common.base.BaseEntity;
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

    @Id @GeneratedValue
    @Column(name = "pla_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pro_id", nullable = false)
    private Project project;

    @Column(name = "pla_title")
    private String title;

    @Builder
    public ProjectPlatform(Project project, String title){
        this.project = project;
        this.title = title;
    }

}