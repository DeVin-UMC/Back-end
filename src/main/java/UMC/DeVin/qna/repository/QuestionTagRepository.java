package UMC.DeVin.qna.repository;

import UMC.DeVin.qna.entity.Question;
import UMC.DeVin.qna.entity.QuestionTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long> {
    List<QuestionTag> findByQuestion(Question question, Pageable pageable);
    List<Question> findByTitleContaining(String title, Pageable pageable);
}
