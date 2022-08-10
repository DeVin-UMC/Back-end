package UMC.DeVin.heart;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.heart.dto.PostHeartAnswerDto;
import UMC.DeVin.heart.dto.PostHeartQuestionDto;
import UMC.DeVin.heart.entity.HeartAnswer;
import UMC.DeVin.heart.entity.HeartQuestion;
import UMC.DeVin.heart.entity.type.Type;
import UMC.DeVin.heart.repository.HeartAnswerRepository;
import UMC.DeVin.heart.repository.HeartQuestionRepository;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.repository.MemberRepository;
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
    
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final HeartAnswerRepository heartAnswerRepository;
    private final HeartQuestionRepository heartQuestionRepository;

    public void likeQuestion(PostHeartQuestionDto dto) throws BaseException {
        // 추천 누른 사람
        Member member = memberRepository.findById(dto.getMemberId()).orElse(null);
        if(member.equals(null)){
            throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID);
        }
        // 추천 누른 질문
        Question question = questionRepository.findById(dto.getQuestionId()).orElse(null);
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

    public void likeAnswer(PostHeartAnswerDto dto) throws BaseException{
        // 추천 누른 사람
        Member member = memberRepository.findById(dto.getMemberId()).orElse(null);
        if(member.equals(null)){
            throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID);
        }
        // 추천 누른 답변
        Answer answer = answerRepository.findById(dto.getAnswerId()).orElse(null);
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

    public void unlikeQuestion(PostHeartQuestionDto dto) throws BaseException {
        // 비추천 누른 사람
        Member member = memberRepository.findById(dto.getMemberId()).orElse(null);
        if(member.equals(null)){
            throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID);
        }
        // 비추천 누른 질문
        Question question = questionRepository.findById(dto.getQuestionId()).orElse(null);
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

    public void unlikeAnswer(PostHeartAnswerDto dto) throws BaseException {
        // 비추천 누른 사람
        Member member = memberRepository.findById(dto.getMemberId()).orElse(null);
        if(member.equals(null)){
            throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID);
        }
        // 비추천 누른 답변
        Answer answer = answerRepository.findById(dto.getAnswerId()).orElse(null);
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










