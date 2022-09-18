package UMC.DeVin.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "PROJECT_RECRUITMENT")
public class ProjectRecruitment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rec_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="pro_id",nullable = false)
    @JsonIgnore
    private Project project;

    @Column(name = "rec_title",nullable = false)
    private String title;

    @Column(name = "rec_language",nullable = false)
    private String language;

    @Column(name="rec_num",nullable = false)
    private int num;

    protected ProjectRecruitment(){}

    public static ProjectRecruitment createRecruitment(Project project, String title, String language, int num) {
        ProjectRecruitment newRecruitment = new ProjectRecruitment();
        newRecruitment.project = project;
        newRecruitment.language = language;
        newRecruitment.title = title;
        newRecruitment.num = num;

        return newRecruitment;
    }

}