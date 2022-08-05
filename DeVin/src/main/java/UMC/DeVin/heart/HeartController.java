package UMC.DeVin.heart;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.heart.dto.HeartAnswerDto;
import UMC.DeVin.heart.dto.HeartQuestionDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/questions/like")
    public BaseResponse<String> likeQuestion(@RequestBody HeartQuestionDto dto) throws BaseException {
        // dto 값 확인
        if(dto.getMemberId().equals(null) || dto.getQuestionId().equals(null)){
            return new BaseResponse<>(BaseResponseStatus.REQUEST_ERROR);
        }

        heartService.likeQuestion(dto);
        return new BaseResponse<>("질문 추천 완료 !");
    }

    @PostMapping("/answers/like")
    public BaseResponse<String> likeAnswer(@RequestBody HeartAnswerDto dto) throws BaseException {
        if(dto.getMemberId().equals(null) || dto.getAnswerId().equals(null)){
            return new BaseResponse<>(BaseResponseStatus.REQUEST_ERROR);
        }
        heartService.likeAnswer(dto);
        return new BaseResponse<>("답변 추천 완료 !");
    }

    @DeleteMapping("/questions/unlike/{id}")
    public BaseResponse<String> unlikeQuestion(@PathVariable Long id) throws BaseException {
        heartService.unlikeQuestion(id);
        return new BaseResponse<>("질문 추천 취소 완료 !");
    }

    @DeleteMapping("/answers/unlike/{id}")
    public BaseResponse<String> unlikeAnswer(@PathVariable Long id) throws BaseException {
        heartService.unlikeAnswer(id);
        return new BaseResponse<>("답변 추천 취소 완료 !");
    }
}
