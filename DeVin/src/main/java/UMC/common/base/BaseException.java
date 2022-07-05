package UMC.common.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *  모든 예외는 서비스 로직에서 처리하고, 예외가 필요할 때는 BaseException으로 처리합니다.
 */
@Getter
@Setter
@AllArgsConstructor
public class BaseException extends Exception {
    private BaseResponseStatus status;
}
