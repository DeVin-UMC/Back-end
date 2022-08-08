package UMC.DeVin.qna;

import UMC.DeVin.qna.dto.*;
import UMC.DeVin.qna.entity.Answer;
import UMC.DeVin.qna.entity.Question;
import UMC.DeVin.qna.entity.QuestionTag;
import UMC.DeVin.qna.entity.select.Select;
import UMC.DeVin.qna.repository.AnswerRepository;
import UMC.DeVin.qna.repository.QuestionRepository;
import UMC.DeVin.qna.repository.QuestionTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QnaService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final QuestionTagRepository questionTagRepository;


    public PostQuestionRes createQuestion(PostQuestionReq dto){

        Question question = Question.createQuestion(dto);
        questionRepository.save(question);

        if(!dto.getTagList().isEmpty()){
            for(PostTagReq TagTitle : dto.getTagList()) {
                questionTagRepository.save(QuestionTag.createTag(question, TagTitle.getTitle()));
            }
        }

        return new PostQuestionRes(question.getId());
    }

    public PostAnswerRes createAnswer(PostAnswerReq dto){

        Question findQuestion = questionRepository.findById(dto.getQuestionId()).get();
        Answer answer = Answer.createAnswer(dto, findQuestion);
        answerRepository.save(answer);

        return new PostAnswerRes(answer.getId());
    }

    public String selectAnswer(Long id) {
        Answer findAnswer = answerRepository.findById(id).get();

        if(findAnswer.getSelect() != Select.TRUE) {
            findAnswer.selectAnswer();
            return "답변 채택";

        }else {
            findAnswer.unselectAnswer();
            return "답변 채택 취소";
        }

    }

}
