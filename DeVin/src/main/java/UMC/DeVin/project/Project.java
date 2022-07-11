package UMC.DeVin.project;

import UMC.DeVin.project.level.ProgramLevel;
import UMC.common.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;


@Entity
@NoArgsConstructor
@Getter
@Table(name = "PROJECT")
public class Project extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "pro_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mbr_id", nullable = false)
    private Member member;

    @Column(name = "pro_title", nullable = false)
    private String title;

    @Column(name = "pro_des", columnDefinition = "TEXT", nullable = false)
    private String des;

    @Enumerated(EnumType.STRING)
    @Column(name = "pro_id", nullable = false)
    private ProgramLevel programLevel;

    @Column(name = "pro_img")
    private String img;

    @Builder
    public Project(Member member, String title, String des, ProgramLevel programLevel, String img){
        this.member = member;
        this.title = title;
        this.des = des;
        this.programLevel = programLevel;
        this.img = img;
    }

}