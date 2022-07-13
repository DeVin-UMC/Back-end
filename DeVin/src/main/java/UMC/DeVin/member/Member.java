package UMC.DeVin.member;

import UMC.DeVin.member.role.MemberRole;
import UMC.DeVin.common.base.BaseEntity;
import UMC.DeVin.config.oauth.entity.ProviderType;
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
    private ProviderType division;

    @Column(name = "mbr_nickname")
    private String nickname;


    protected Member() { }


    /**
     *  회원 가입 전, OAuth를 활용한 로그인만 진행되었을 때 사용됩니다.
     */
    public void init(String name, String email, String profileImageUrl, ProviderType division) {
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.division = division;
        this.role = MemberRole.USER;
    }

    /**
     *  회원 가입 시 OAuth를 활용한 로그인 후, 추가정보를 입력할 때 사용됩니다.
     */
    public void updateAdditionalInfo(String nickname) {
        this.nickname = nickname;
    }

    /**
     *  OAuth 로그인 시, 프로필 사진이 바뀌었을 때 사용됩니다.
     */
    public void updateProfileImg(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    /**
     *
     * @param name 이름
     * @param email 이메일
     * @param profileImageUrl 프로필 사진 URL
     * @param division 사용자 구분
     * 해당 메서드로만 Member을 생성합니다.
     */
    public static Member createMember(String name, String email, String profileImageUrl, ProviderType division) {
        Member member = new Member();
        member.init(name, email, profileImageUrl, division);

        return member;
    }




}
