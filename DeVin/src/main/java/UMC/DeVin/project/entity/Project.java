package UMC.DeVin.project.entity;

import UMC.DeVin.project.dto.PostProjectReqDto;
import UMC.DeVin.project.entity.level.ProgramLevel;
import UMC.common.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Table(name = "PROJECT")
@Entity
public class  Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    private Long id;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "mbr_id")
//    private Member member;

    @Column(name = "pro_title", nullable = false)
    private String title;

    @Column(name = "pro_des", columnDefinition = "TEXT", nullable = false)
    private String des;

    @Enumerated(EnumType.STRING)
    @Column(name = "pro_level", nullable = false)
    private ProgramLevel programLevel;

    @Column(name = "pro_img")
    private String img;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectPlatform> projectPlatforms = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectRecruitment> projectRecruitments = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectRegion> projectRegions = new ArrayList<>();

    /* Member 엔티티에 project 추가*/
//    public void setMember(Member member){
//        this.member = member;
//        member.getProjects().add(this);
//    }

    @Builder
    public Project(String title, String des, ProgramLevel programLevel,
                   String img, List<ProjectPlatform> projectPlatforms, List<ProjectRecruitment> projectRecruitments, List<ProjectRegion> projectRegions){
        this.title = title;
        this.des = des;
        this.programLevel = programLevel;
        this.img = img;
        this.projectPlatforms = projectPlatforms;
        this.projectRecruitments = projectRecruitments;
        this.projectRegions = projectRegions;
    }

    /* 게시글 수정 */
    public void updateProject(PostProjectReqDto postProjectReqDto){
        this.title = postProjectReqDto.getTitle();
        this.des = postProjectReqDto.getDes();
    }

}