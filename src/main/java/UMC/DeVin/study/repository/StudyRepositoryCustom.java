package UMC.DeVin.study.repository;

import UMC.DeVin.study.dto.StudyResDTO;
import UMC.DeVin.study.dto.StudySearchCondition;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudyRepositoryCustom {



    //분류, 지역, 난이도
    List<StudyResDTO> findPage(StudySearchCondition condition, Pageable pageable);

    //분류, 지역, 난이도
    List<StudyResDTO> findByNoCondition(Pageable pageable);

    List<StudyResDTO> findByKeyword(String keyword, Pageable pageable);

}
