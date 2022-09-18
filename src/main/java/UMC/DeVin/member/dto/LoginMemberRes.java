package UMC.DeVin.member.dto;

import UMC.DeVin.config.oauth.entity.ProviderType;
import UMC.DeVin.member.role.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginMemberRes {

    private String accessToken;
    private String email;
    private String profileImgUrl;
    private String nickname;
    private ProviderType providerType;
    private MemberRole role;

}
