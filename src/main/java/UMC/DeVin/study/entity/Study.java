package UMC.DeVin.study.entity;

import UMC.DeVin.common.base.BaseEntity;
import UMC.DeVin.member.Member;
import UMC.DeVin.common.Level;
import UMC.DeVin.study.dto.PostStudyReqDTO;
import lombok.Getter;
import javax.persistence.*;


@Entity
@Getter
@Table(name = "STUDY")
public class Study extends BaseEntity {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "std_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mbr_id")
    private Member member;

    @Column(name = "std_title", nullable = false)
    private String title;

    @Column(name = "std_des", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "std_level", nullable = false)
    private Level level;

    @Column(name = "std_rec_num", nullable = false)
    private int recruitNumber;

    @Column(name = "std_img", nullable = true)
    private String imageUrl;



    protected Study() { }

    private Study(String title, String description, Level level, int recruitNumber, String imageUrl, Member member) {
        this.title = title;
        this.description = description;
        this.level = level;
        this.recruitNumber = recruitNumber;
        this.imageUrl = imageUrl;
        this.member = member;
    }

    /**
     * Study 엔티티를 생성하는 함수입니다.
     * Study 엔티티는 해당 함수로만 생성됩니다.
     * @param dto 컨트롤러에서 RequestBody로 입력받은 DTO
     * @param imageUrl 이미지 업로드 후 반환된 이미지 URL
     * @param member 스터디를 생성한 사용자
     * @return 생성된 Study 엔티티
     */
    public static Study createStudy(PostStudyReqDTO dto, String imageUrl, Member member) {
        return new Study(dto.getTitle(), dto.getDescription(), dto.getLevel(), dto.getRecruitNumber(),
                imageUrl, member);
    }
    public void updateStudy(PostStudyReqDTO postStudyReqDTO){
        this.title = postStudyReqDTO.getTitle();
        this.description = postStudyReqDTO.getDescription();
    }

}