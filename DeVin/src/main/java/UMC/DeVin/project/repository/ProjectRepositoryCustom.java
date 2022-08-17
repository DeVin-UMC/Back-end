package UMC.DeVin.project.repository;

import UMC.DeVin.project.dto.ProjectRes;
import UMC.DeVin.project.dto.ProjectSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectRepositoryCustom {

    Page<ProjectRes> findPage(ProjectSearchCondition condition, Pageable pageable);
    Page<ProjectRes> findByKeyword(String keyword, Pageable pageable);

}
