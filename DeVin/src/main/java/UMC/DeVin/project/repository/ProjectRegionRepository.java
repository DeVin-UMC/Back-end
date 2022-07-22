package UMC.DeVin.project.repository;

import UMC.DeVin.project.entity.Project;
import UMC.DeVin.project.entity.ProjectRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProjectRegionRepository extends JpaRepository<ProjectRegion,Long> {
    List<ProjectRegion> findByProject(Project project);
}
