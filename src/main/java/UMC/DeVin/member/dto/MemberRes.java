package UMC.DeVin.member.dto;

import UMC.DeVin.member.role.MemberRole;
import UMC.DeVin.config.oauth.entity.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRes {
    private Long id;
    private String email;
    private String profileImgUrl;
    private String nickname;
    private ProviderType providerType;
    private MemberRole role;
}
