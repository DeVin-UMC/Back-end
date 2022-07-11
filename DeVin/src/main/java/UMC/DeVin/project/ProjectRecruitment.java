package UMC.DeVin.project;

import UMC.common.base.BaseEntity;
import lombok.Builder;
import lombok.CustomLog;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.ClassRule;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "PROJECT_RECRUITMENT")
public class ProjectRecruitment {

    @Id @GeneratedValue
    @Column(name = "rec_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="pro_id", nullable = false)
    private Project project;

    @Column(name = "rec_title", nullable = false)
    private String title;

    @Column(name="rec_num", nullable = false)
    private int num;

    @Builder
    public ProjectRecruitment(Project project, String title, int num){
        this.project = project;
        this.title = title;
        this.num = num;
    }
}