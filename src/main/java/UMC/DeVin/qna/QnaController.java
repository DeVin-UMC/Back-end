package UMC.DeVin.qna;

import UMC.DeVin.auth.OAuthLoginUserUtil;
import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.member.Member;
import UMC.DeVin.qna.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "QnA", description = "Q&A API")
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
    @Operation(summary = "질문 생성", description = "질문이 생성됩니다.", tags = { "QnaController" })
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
    @Operation(summary = "답변 생성", description = "답변이 생성됩니다.", tags = { "QnaController" })
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
    @Operation(summary = "답변 채택/채택 취소", description = "답변을 채택 혹은 채택 취소 해줍니다.", tags = { "QnaController" })
    @PatchMapping("/answers/{id}")
    public BaseResponse<String> selectAnswer(@Parameter(example = "1") @PathVariable Long id) throws BaseException {

        String res = qnaService.selectAnswer(id);
        return new BaseResponse<>(res);

    }

    /**
     * qna 페이징 API
     * [GET] /qna
     * @return List<GetQnaDto>
     */
    @Operation(summary = "Q&A 페이징", description = "qna를 페이징 처리 합니다. ", tags = { "QnaController" })
    @GetMapping("/qna")
    public BaseResponse<List<GetQnaDto>> pageQna(@PageableDefault(direction = Sort.Direction.DESC) Pageable pageable) throws BaseException {
        return new BaseResponse<>(qnaService.pageQna(pageable));
    }

    /**
     * qna 검색 API
     * [GET] /qna/search?keyword=검색어
     * @return List<GetQnaDto>
     */
    @Operation(summary = "Q&A 검색", description = "검색어(keyword)를 이용해 qna를 검색합니다.", tags = { "QnaController" })
    @GetMapping("/qna/search")
    public BaseResponse<List<GetQnaDto>> searchQna(@Parameter(example = "검색어") @RequestParam String keyword, @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable) throws BaseException {
        // 검색어 2글자 이상 입력
        if(keyword.length()<2){
            return new BaseResponse<>(BaseResponseStatus.REQUEST_KEYWORD);
        }
        return new BaseResponse<>(qnaService.searchQna(keyword,pageable));
    }

}
