package UMC.DeVin.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static UMC.DeVin.auth.IpAddressUtil.getRemoteAddr;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEMBER_REFRESH_TOKEN")
public class MemberRefreshToken {
    @JsonIgnore
    @Id
    @Column(name = "refresh_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenSeq;

    @Column(name = "oauth_user_id", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String userId;


    @Column(name = "refresh_token_value", length = 256)
    @NotNull
    @Size(max = 256)
    private String refreshToken;

    @Column(name = "user_ip")
    @NotBlank
    private String userIpAddress;

    @Column(name = "mbr_id")
    private Long memberId;

    public MemberRefreshToken(
            @NotNull @Size(max = 64) String userId,
            @NotNull @Size(max = 256) String refreshToken,
            @NotNull Long memberId,
            HttpServletRequest request
    ) {
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.memberId = memberId;
        this.userIpAddress = getRemoteAddr(request);
    }
}