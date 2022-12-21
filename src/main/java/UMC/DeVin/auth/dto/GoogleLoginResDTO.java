package UMC.DeVin.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 구글 로그인 시 클라이언트에게 넘겨주는 response DTO 입니다.
 *
 * @author CHO Min HO
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleLoginResDTO {
    private GoogleUserDTO userDTO;
    private boolean isSignUp;
    private String accessToken;
}
