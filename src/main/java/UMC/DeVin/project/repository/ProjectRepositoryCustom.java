package UMC.DeVin.project.repository;

import UMC.DeVin.project.dto.ProjectRes;
import UMC.DeVin.project.dto.ProjectSearchCondition;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectRepositoryCustom {

    List<ProjectRes> findPage(ProjectSearchCondition condition, Pageable pageable);
    List<ProjectRes> findByKeyword(String keyword, Pageable pageable);

}
