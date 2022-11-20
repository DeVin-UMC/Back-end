package UMC.DeVin.study.repository;

import UMC.DeVin.study.entity.Study;
import UMC.DeVin.study.entity.StudyRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRegionRepository extends JpaRepository<StudyRegion, Long> {
    public List<StudyRegion> findByStudy(Study study);
}
