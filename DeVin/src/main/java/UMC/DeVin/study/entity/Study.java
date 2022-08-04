package UMC.DeVin.study.entity;

import UMC.DeVin.common.base.BaseEntity;
import UMC.DeVin.member.Member;
import lombok.Getter;

import javax.persistence.*;


@Entity
@Getter
@Table(name = "STUDY")
public class Study extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "std_id", nullable = false)
    private int id;

    @Column(name = "std_title", nullable = false)
    private String title;

    @Column(name = "std_des", nullable = false)
    private String des;

    @Column(name = "std_level", nullable = false)
    private String level;

    @Column(name = "std_rec_num", nullable = false)
    private int num;

    @Column(name = "std_img",nullable = true)
    private String img;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mbr_id")
    private Member member;

    protected Study() {}
}