package UMC.DeVin.heart.repository;

import UMC.DeVin.heart.entity.HeartQuestion;
import UMC.DeVin.member.Member;
import UMC.DeVin.qna.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartQuestionRepository extends JpaRepository<HeartQuestion,Long> {
    Optional<HeartQuestion> findByMemberAndQuestion(Member member, Question question);
}
