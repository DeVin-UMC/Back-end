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
import java.util.stream.Collectors;


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

    @Column(name = "pro_title")
    private String title;

    @Column(name = "pro_des", columnDefinition = "TEXT")
    private String des;

    @Enumerated(EnumType.STRING)
    @Column(name = "pro_level")
    private ProgramLevel programLevel;

    @Column(name = "pro_img")
    private String img;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectPlatform> projectPlatforms = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectRecruitment> projectRecruitments = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectRegion> projectRegions = new ArrayList<>();


    /* Dto -> Entity */
    public Project(PostProjectReqDto dto){
        this.title = dto.getTitle();
        this.des= dto.getDes();
        this.programLevel = programLevel.valueOf(dto.getProgramLevel());
        this.img = dto.getImg();
        this.projectPlatforms = dto.getPlatforms().stream()
                .map( platformDto-> new ProjectPlatform(platformDto))
                .collect(Collectors.toList());
        this.projectRecruitments = dto.getRecruitments().stream()
                .map( recruitmentDto-> new ProjectRecruitment(recruitmentDto))
                .collect(Collectors.toList());
        this.projectRegions = dto.getRegions().stream()
                .map( regionDto-> new ProjectRegion(regionDto))
                .collect(Collectors.toList());

    }

    /* 게시글 수정 */
    public void updateProject(PostProjectReqDto postProjectReqDto){
        this.title = postProjectReqDto.getTitle();
        this.des = postProjectReqDto.getDes();
    }

}