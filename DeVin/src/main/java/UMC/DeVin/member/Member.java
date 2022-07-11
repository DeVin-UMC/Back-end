package UMC.DeVin.member;

import UMC.DeVin.member.division.MemberDivision;
import UMC.DeVin.member.role.MemberRole;
import UMC.common.base.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "mbr_id")
    private Long id;

    @Column(name = "mbr_name")
    private String name;

    @Column(name = "mbr_email")
    private String email;

    @Column(name = "mbr_prof_img")
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "mbr_role")
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "mbr_div")
    private MemberDivision division;

    @Column(name = "mbr_nickname")
    private String nickname;

    protected Member() { }

    private void init(String name, String email, String profileImageUrl, MemberDivision division, String nickname) {
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.division = division;
        this.nickname = nickname;
    }

    /**
     *
     * @param name 이름
     * @param email 이메일
     * @param profileImageUrl 프로필 사진 URL
     * @param division 사용자 구분
     * @param nickname 닉네임
     * 해당 메서드로만 Member을 생성합니다.
     */
    public static Member createMember(String name, String email, String profileImageUrl, MemberDivision division, String nickname) {
        Member member = new Member();
        member.init(name, email, profileImageUrl, division, nickname);

        return member;
    }



}
