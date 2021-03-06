package UMC.DeVin.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  accessToken DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenRes {

    private String accessToken;

}
