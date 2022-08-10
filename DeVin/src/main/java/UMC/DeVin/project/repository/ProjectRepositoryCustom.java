package UMC.DeVin.project.repository;

import UMC.DeVin.project.dto.GetProjectDto;
import UMC.DeVin.project.dto.ProjectSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectRepositoryCustom {

    Page<GetProjectDto> findPage(ProjectSearchCondition condition, Pageable pageable);
    Page<GetProjectDto> findByKeyword(String keyword, Pageable pageable);

}
