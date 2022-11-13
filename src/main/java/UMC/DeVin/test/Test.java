package UMC.DeVin.test;

import UMC.DeVin.common.base.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "테스트", description = "테스트 설명", tags = { "Test Controller" })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping("/test")
    public String test() throws BaseException {
        // 성공
        return "success";
    }

}


