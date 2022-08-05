package UMC.DeVin.qna;

import UMC.DeVin.qna.dto.PostAnswerReq;
import UMC.DeVin.qna.dto.PostQuestionReq;
import UMC.DeVin.qna.dto.PostQuestionRes;
import UMC.DeVin.qna.dto.PostTagReq;
import UMC.DeVin.qna.entity.Answer;
import UMC.DeVin.qna.entity.Question;
import UMC.DeVin.qna.entity.QuestionTag;
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

    public void createAnswer(PostAnswerReq dto){

        Question findQuestion = questionRepository.findById(dto.getQuestionIdx()).get();
        answerRepository.save(Answer.createAnswer(dto, findQuestion));
    }

    public void selectAnswer(Long id) {
        Answer findAnswer = answerRepository.findById(id).get();
        findAnswer.selectAnswer();
    }

}
