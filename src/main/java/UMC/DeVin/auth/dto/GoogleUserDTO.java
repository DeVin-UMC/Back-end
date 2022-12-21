package UMC.DeVin.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * credential을 바탕으로 구글 사용자 정보를 담고 있는 dto입니다.
 *
 * @author CHO MIN Ho
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleUserDTO {
    private String email;
    private String pictureUrl;
    private String name;
}
