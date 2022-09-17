package UMC.DeVin.qna.repository;

import UMC.DeVin.qna.entity.Answer;
import UMC.DeVin.qna.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestion(Question question, Pageable pageable);
}
