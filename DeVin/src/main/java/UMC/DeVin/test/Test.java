package UMC.DeVin.test;

import UMC.DeVin.common.base.BaseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 *  컨트롤러 테스트용입니다.
 */


@RestController
public class Test {

    @GetMapping("/test")
    public String test() throws BaseException {
        // 성공
        return "success";
    }

}
