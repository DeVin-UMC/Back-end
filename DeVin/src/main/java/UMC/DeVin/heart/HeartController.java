package UMC.DeVin.heart;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.heart.dto.PostHeartAnswerDto;
import UMC.DeVin.heart.dto.PostHeartQuestionDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class HeartController {

    private final HeartService heartService;

    /**
     * 질문 추천 API
     * [POST] /questions/like
     * @return BaseResponse<String>
     */
    @PostMapping("/questions/like")
    public BaseResponse<String> likeQuestion(@RequestBody PostHeartQuestionDto dto) throws BaseException {
        heartService.likeQuestion(dto);
        return new BaseResponse<>("질문 추천 완료 !");
    }

    /**
     * 질문 비추천 API
     * [POST] /questions/unlike
     * @return BaseResponse<String>
     */
    @PostMapping("/questions/unlike")
    public BaseResponse<String> unlikeQuestion(@RequestBody PostHeartQuestionDto dto) throws BaseException {
        heartService.unlikeQuestion(dto);
        return new BaseResponse<>("질문 비추천 완료 !");
    }

    /**
     * 답변 추천 API
     * [POST] /answers/like
     * @return BaseResponse<String>
     */
    @PostMapping("/answers/like")
    public BaseResponse<String> likeAnswer(@RequestBody PostHeartAnswerDto dto) throws BaseException {
        heartService.likeAnswer(dto);
        return new BaseResponse<>("답변 추천 완료 !");
    }

    /**
     * 답변 추천 API
     * [POST] /answers/unlike
     * @return BaseResponse<String>
     */
    @PostMapping("/answers/unlike")
    public BaseResponse<String> unlikeAnswer(@RequestBody PostHeartAnswerDto dto) throws BaseException {
        heartService.unlikeAnswer(dto);
        return new BaseResponse<>("답변 비추천 완료 !");
    }

    /**
     * 질문 추천 취소 API
     * [DELETE] /questions/like/1
     * @return BaseResponse<String>
     */
    @DeleteMapping("/questions/like/{id}")
    public BaseResponse<String> cancelLikeQuestion(@PathVariable Long id) throws BaseException {
        heartService.cancelLikeQuestion(id);
        return new BaseResponse<>("질문 추천 취소 완료 !");
    }

    /**
     * 답변 추천 취소 API
     * [DELETE] /answers/like/1
     * @return BaseResponse<String>
     */
    @DeleteMapping("/answers/like/{id}")
    public BaseResponse<String> cancelLikeAnswer(@PathVariable Long id) throws BaseException {
        heartService.cancelLikeAnswer(id);
        return new BaseResponse<>("답변 추천 취소 완료 !");
    }

}
