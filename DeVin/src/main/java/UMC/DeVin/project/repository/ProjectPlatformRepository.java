package UMC.DeVin.project.repository;

import UMC.DeVin.project.entity.Project;
import UMC.DeVin.project.entity.ProjectPlatform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectPlatformRepository extends JpaRepository<ProjectPlatform,Long> {

    List<ProjectPlatform> findByProject(Project project);
}
