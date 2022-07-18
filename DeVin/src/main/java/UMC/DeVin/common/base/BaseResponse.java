package UMC.DeVin.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static UMC.DeVin.common.base.BaseResponseStatus.SUCCESS;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"responseTime", "isSuccess", "code", "message", "result"})
public class BaseResponse<T> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime responseTime;
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String message;
    private final int code;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 성공한 경우
    public BaseResponse(T result) {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
        this.responseTime = LocalDateTime.now();
    }

    // 요청에 실패한 경우
    public BaseResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
        this.responseTime = LocalDateTime.now();
    }

    public static ResponseEntity<BaseResponse<BaseResponseStatus>> toResponseEntity(BaseException e) {
        return ResponseEntity
                .status(e.getStatus().getHttpStatus())
                .body(new BaseResponse<>(e.getStatus()));
    }
}