package UMC.DeVin.heart;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.heart.dto.HeartAnswerDto;
import UMC.DeVin.heart.dto.HeartQuestionDto;
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
import java.awt.*;
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

    public void likeQuestion(HeartQuestionDto dto) {
        // 추천 누른 사용자
        Member member = memberRepository.findById(dto.getMemberId()).orElse(null);
        // 추천한 질문
        Question question = questionRepository.findById(dto.getQuestionId()).orElse(null);
        HeartQuestion heartQuestion = heartQuestionRepository.findByMemberAndQuestion(member,question).orElse(null);

        // 중복 추천 방지
        if(heartQuestion == null || heartQuestion.getType() != Type.LIKE){
            heartQuestionRepository.save(HeartQuestion.createLikeQuestion(member,question));
        }

    }

    public void likeAnswer(HeartAnswerDto dto) {
        Member member = memberRepository.findById(dto.getMemberId()).orElse(null);
        Answer answer = answerRepository.findById(dto.getAnswerId()).orElse(null);
        HeartAnswer heartAnswer = heartAnswerRepository.findByMemberAndAnswer(member,answer).orElse(null);

        if(dto.getAnswerId() != null && heartAnswer == null || heartAnswer.getType() != Type.LIKE){
            heartAnswerRepository.save(HeartAnswer.createLikeAnswer(member,answer));
        }
    }

    public void unlikeQuestion(Long id) {
        // HeartQuestion heartQuestion = heartQuestionRepository.findById(id).orElse(null);
        // heartQuestion.unlike();
        // 이 코드 실행하면 계속 쿼리문만 실행됨 .. ㅠㅠ

        heartQuestionRepository.deleteById(id);
    }

    public void unlikeAnswer(Long id) {
        heartAnswerRepository.deleteById(id);
    }
}
