package UMC.DeVin.study.repository;

import UMC.DeVin.study.entity.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {
    Optional<Study> findByTitle(String title);
    Page<Study> findAll(Pageable pageable);
}
