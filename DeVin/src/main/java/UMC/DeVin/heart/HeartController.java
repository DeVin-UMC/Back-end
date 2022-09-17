package UMC.DeVin.heart;

import UMC.DeVin.auth.OAuthLoginUserUtil;
import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.member.Member;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class HeartController {

    private final HeartService heartService;
    private final OAuthLoginUserUtil oAuthLoginUserUtil;

    /**
     * 질문 추천 API
     * [POST] /questions/like
     * @return BaseResponse<String>
     */
    @PostMapping("/questions/like/{id}")
    public BaseResponse<String> likeQuestion(@PathVariable Long id) throws BaseException {
        Member loginMember = oAuthLoginUserUtil.getLoginMemberWithContext();
        heartService.likeQuestion(id, loginMember);
        return new BaseResponse<>("질문 추천 완료 !");
    }

    /**
     * 질문 비추천 API
     * [POST] /questions/unlike
     * @return BaseResponse<String>
     */
    @PostMapping("/questions/unlike/{id}")
    public BaseResponse<String> unlikeQuestion(@PathVariable Long id) throws BaseException {
        Member loginMember = oAuthLoginUserUtil.getLoginMemberWithContext();
        heartService.unlikeQuestion(id, loginMember);
        return new BaseResponse<>("질문 비추천 완료 !");
    }

    /**
     * 답변 추천 API
     * [POST] /answers/like
     * @return BaseResponse<String>
     */
    @PostMapping("/answers/like/{id}")
    public BaseResponse<String> likeAnswer(@PathVariable Long id) throws BaseException {
        Member loginMember = oAuthLoginUserUtil.getLoginMemberWithContext();
        heartService.likeAnswer(id, loginMember);
        return new BaseResponse<>("답변 추천 완료 !");
    }

    /**
     * 답변 추천 API
     * [POST] /answers/unlike
     * @return BaseResponse<String>
     */
    @PostMapping("/answers/unlike/{id}")
    public BaseResponse<String> unlikeAnswer(@PathVariable Long id) throws BaseException {
        Member loginMember = oAuthLoginUserUtil.getLoginMemberWithContext();
        heartService.unlikeAnswer(id, loginMember);
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
