package UMC.DeVin.project.entity;

import UMC.DeVin.common.base.BaseEntity;
import UMC.DeVin.member.Member;
import UMC.DeVin.project.dto.PostProjectReqDto;
import UMC.DeVin.project.entity.level.ProgramLevel;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;


@Getter
@Table(name = "PROJECT")
@Entity
public class  Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mbr_id")
    private Member member;

    @Column(name = "pro_title")
    private String title;

    @Column(name = "pro_des", columnDefinition = "TEXT")
    private String des;

    @Enumerated(EnumType.STRING)
    @Column(name = "pro_level")
    private ProgramLevel programLevel;

    @Column(name = "pro_img")
    private String img;

    protected Project(){ }

    /* 프로젝트 생성 */
    public static Project createProject(PostProjectReqDto dto, Member member){
        Project newProject = new Project();
        newProject.member = member;
        newProject.title = dto.getTitle();
        newProject.des = dto.getDes();
        newProject.programLevel = ProgramLevel.valueOf(dto.getProgramLevel());
        newProject.img = dto.getImg();

        return newProject;
    }

    /* 게시글 수정 */
    public void updateProject(PostProjectReqDto postProjectReqDto){
        this.title = postProjectReqDto.getTitle();
        this.des = postProjectReqDto.getDes();
    }

}