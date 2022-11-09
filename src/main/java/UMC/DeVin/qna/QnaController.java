package UMC.DeVin.qna;

import UMC.DeVin.auth.OAuthLoginUserUtil;
import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.member.Member;
import UMC.DeVin.qna.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class QnaController {

    private final QnaService qnaService;
    private final OAuthLoginUserUtil oAuthLoginUserUtil;

    /**
     * 질문 생성 API
     * [POST] /questions
     * @return BaseResponse<PostQuestionRes>
     */
    @PostMapping("/questions")
    public BaseResponse<PostQuestionRes> createQuestion(@RequestBody @Valid PostQuestionReq dto) throws BaseException {

        Member writer = oAuthLoginUserUtil.getLoginMemberWithContext();
        System.out.println(writer);
        System.out.println("------------");
        PostQuestionRes questionRes = qnaService.createQuestion(dto,writer);
        return new BaseResponse<>(questionRes);

    }

    /**
     * 답변 생성 API
     * [POST] /answers
     * @return BaseResponse<PostAnswerRes>
     */
    @PostMapping("/answers")
    public BaseResponse<PostAnswerRes> createAnswer(@RequestBody @Valid PostAnswerReq dto) throws BaseException {

        Member writer = oAuthLoginUserUtil.getLoginMemberWithContext();
        PostAnswerRes answerRes = qnaService.createAnswer(dto, writer);
        return new BaseResponse<>(answerRes);

    }

    /**
     * 답변 채택 / 채택 취소 API
     * [PATCH] /answers/1
     * @return BaseResponse<String>
     */
    @PatchMapping("/answers/{id}")
    public BaseResponse<String> selectAnswer(@PathVariable Long id) throws BaseException {

        String res = qnaService.selectAnswer(id);
        return new BaseResponse<>(res);

    }

    /**
     * qna 페이징 API
     * [GET] /qna
     * @return List<GetQnaDto>
     */
    @GetMapping("/qna")
    public BaseResponse<List<GetQnaDto>> pageQna(@PageableDefault(direction = Sort.Direction.DESC) Pageable pageable) throws BaseException {
        return new BaseResponse<>(qnaService.pageQna(pageable));
    }

    /**
     * qna 검색 API
     * [GET] /qna/search?keyword=검색어
     * @return List<GetQnaDto>
     */
    @GetMapping("/qna/search")
    public BaseResponse<List<GetQnaDto>> searchQna(@RequestParam String keyword, @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable) throws BaseException {
        // 검색어 2글자 이상 입력
        if(keyword.length()<2){
            return new BaseResponse<>(BaseResponseStatus.REQUEST_KEYWORD);
        }
        return new BaseResponse<>(qnaService.searchQna(keyword,pageable));
    }

}
