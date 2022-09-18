package UMC.DeVin.heart.repository;

import UMC.DeVin.heart.entity.HeartAnswer;
import UMC.DeVin.member.Member;
import UMC.DeVin.qna.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartAnswerRepository extends JpaRepository<HeartAnswer, Long> {
    Optional<HeartAnswer> findByMemberAndAnswer(Member member, Answer answer);
}
