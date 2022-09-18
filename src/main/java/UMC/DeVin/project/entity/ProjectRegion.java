package UMC.DeVin.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "PROJECT_REGION")
public class ProjectRegion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reg_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pro_id",nullable = false)
    @JsonIgnore
    private Project project;

    @Column(name = "rec_title",nullable = false)
    private String title;

    protected ProjectRegion(){}
    public static ProjectRegion createRegion(Project project, String title) {
        ProjectRegion newRegion = new ProjectRegion();
        newRegion.project = project;
        newRegion.title = title;

        return newRegion;
    }

}
