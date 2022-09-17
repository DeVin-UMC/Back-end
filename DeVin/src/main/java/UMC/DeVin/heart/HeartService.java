package UMC.DeVin.heart;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.heart.entity.HeartAnswer;
import UMC.DeVin.heart.entity.HeartQuestion;
import UMC.DeVin.heart.entity.type.Type;
import UMC.DeVin.heart.repository.HeartAnswerRepository;
import UMC.DeVin.heart.repository.HeartQuestionRepository;
import UMC.DeVin.member.Member;
import UMC.DeVin.qna.entity.Answer;
import UMC.DeVin.qna.entity.Question;
import UMC.DeVin.qna.repository.AnswerRepository;
import UMC.DeVin.qna.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class HeartService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final HeartAnswerRepository heartAnswerRepository;
    private final HeartQuestionRepository heartQuestionRepository;

    public void likeQuestion(Long id, Member member) throws BaseException {

        // 추천 누른 질문
        Question question = questionRepository.findById(id).orElse(null);
        if(question.equals(null)){
            throw new BaseException(BaseResponseStatus.EMPTY_QUESTION_ID);
        }

        Optional<HeartQuestion> findQuestion = heartQuestionRepository.findByMemberAndQuestion(member, question);
        if(findQuestion.isPresent()){
            // 추천되어 있던 경우
            if(findQuestion.get().getType().equals(Type.LIKE)){
                throw new BaseException(BaseResponseStatus.ALREADY_LIKE);
            }
            // 비추천되어 있던 경우 -> 비추천을 추천으로 변경
            else if(findQuestion.get().getType().equals(Type.UNLIKE)){
                findQuestion.get().like();
            }
        }else {
            HeartQuestion heartQuestion = HeartQuestion.createLikeQuestion(member, question);
            heartQuestionRepository.save(heartQuestion);
        }


    }

    public void likeAnswer(Long id, Member member) throws BaseException{

        // 추천 누른 답변
        Answer answer = answerRepository.findById(id).orElse(null);
        if(answer.equals(null)){
            throw new BaseException(BaseResponseStatus.EMPTY_ANSWER_ID);
        }

        Optional<HeartAnswer> findAnswer = heartAnswerRepository.findByMemberAndAnswer(member, answer);
        if(findAnswer.isPresent()){
            // 추천되어 있던 경우
            if(findAnswer.get().getType().equals(Type.LIKE)){
                throw new BaseException(BaseResponseStatus.ALREADY_LIKE);
            }
            // 비추천되어 있던 경우 -> 추천으로 변경
            else if(findAnswer.get().getType().equals(Type.UNLIKE)){
                findAnswer.get().like();
            }
        }else {
            HeartAnswer likeAnswer = HeartAnswer.createLikeAnswer(member, answer);
            heartAnswerRepository.save(likeAnswer);
        }


    }

    public void unlikeQuestion(Long id, Member member) throws BaseException {

        // 비추천 누른 질문
        Question question = questionRepository.findById(id).orElse(null);
        if(question.equals(null)){
            throw new BaseException(BaseResponseStatus.EMPTY_QUESTION_ID);
        }

        Optional<HeartQuestion> findQuestion = heartQuestionRepository.findByMemberAndQuestion(member, question);
        if(findQuestion.isPresent()){
            // 비추천되어 있던 경우
            if(findQuestion.get().getType().equals(Type.UNLIKE)){
                throw new BaseException(BaseResponseStatus.ALREADY_UNLIKE);
            }
            // 추천되어 있던 경우 -> 비추천
            else if(findQuestion.get().getType().equals(Type.LIKE)){
                findQuestion.get().unlike();
            }
        }else {
            HeartQuestion unlikeQuestion = HeartQuestion.createUnlikeQuestion(member, question);
            heartQuestionRepository.save(unlikeQuestion);
        }

    }

    public void unlikeAnswer(Long id, Member member) throws BaseException {

        // 비추천 누른 답변
        Answer answer = answerRepository.findById(id).orElse(null);
        if(answer.equals(null)){
            throw new BaseException(BaseResponseStatus.EMPTY_ANSWER_ID);
        }

        Optional<HeartAnswer> findAnswer = heartAnswerRepository.findByMemberAndAnswer(member, answer);
        if(findAnswer.isPresent()){
            // 비추천되어 있던 경우
            if(findAnswer.get().getType().equals(Type.UNLIKE)){
                throw new BaseException(BaseResponseStatus.ALREADY_UNLIKE);
            }
            // 추천되어 있던 경우 -> 추천 삭제
            else if(findAnswer.get().getType().equals(Type.LIKE)){
                findAnswer.get().unlike();
            }
        }else {
            HeartAnswer unlikeAnswer = HeartAnswer.createUnlikeAnswer(member, answer);
            heartAnswerRepository.save(unlikeAnswer);
        }

    }


    public void cancelLikeQuestion(Long id) {
        heartQuestionRepository.deleteById(id);
    }

    public void cancelLikeAnswer(Long id) {
        heartAnswerRepository.deleteById(id);
    }



}










