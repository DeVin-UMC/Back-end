package UMC.DeVin.qna;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.qna.dto.PostAnswerReq;
import UMC.DeVin.qna.dto.PostAnswerRes;
import UMC.DeVin.qna.dto.PostQuestionReq;
import UMC.DeVin.qna.dto.PostQuestionRes;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    /**
     * 질문 생성 API
     * [POST] /questions
     * @return BaseResponse<PostQuestionRes>
     */
    @PostMapping("/questions")
    public BaseResponse<PostQuestionRes> createQuestion(@RequestBody @Valid PostQuestionReq dto) throws BaseException {

        PostQuestionRes questionRes = qnaService.createQuestion(dto);
        return new BaseResponse<>(questionRes);

    }

    /**
     * 답변 생성 API
     * [POST] /answers
     * @return BaseResponse<PostAnswerRes>
     */
    @PostMapping("/answers")
    public BaseResponse<PostAnswerRes> createAnswer(@RequestBody @Valid PostAnswerReq dto) throws BaseException {

        PostAnswerRes answerRes = qnaService.createAnswer(dto);
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


}
