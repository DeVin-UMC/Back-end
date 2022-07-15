package UMC.DeVin.auth.not_use;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  login 컨트롤러와 더불어 사용하지 않습니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthReq {
    private String id;
    private String password;
}
