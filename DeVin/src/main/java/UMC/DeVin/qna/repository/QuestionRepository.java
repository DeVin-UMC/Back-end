package UMC.DeVin.qna.repository;

import UMC.DeVin.qna.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Long>{
    Page<Question> findAll(Pageable pageable);
    Page<Question> findByTitleContaining(String title, Pageable pageable);
    Page<Question> findByContentContaining(String content, Pageable pageable);
}
