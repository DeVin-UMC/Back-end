package UMC.DeVin.project.entity;

import UMC.DeVin.project.dto.PostProjectReqDto;
import UMC.DeVin.project.dto.RecruitmentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "PROJECT_RECRUITMENT")
public class ProjectRecruitment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rec_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="pro_id")
    private Project project;

    @Column(name = "rec_title")
    private String title;

    @Column(name="rec_num")
    private int num;

    public ProjectRecruitment(RecruitmentDto dto){
        this.title = dto.getTitle();
        this.num = dto.getNum();
    }

    public void setProject(Project project){
        this.project=project;
    }

}