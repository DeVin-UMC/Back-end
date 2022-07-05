package UMC.DeVin.test;

import UMC.common.base.BaseException;
import UMC.common.base.BaseResponse;
import UMC.common.base.BaseResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  컨틀롤러 테스트용입니다.
 */


@RestController
public class Test {

    @PostMapping("/test")
    public String test(@RequestBody @Valid TestDTO dto) throws BaseException {
        return "success";
    }

}
