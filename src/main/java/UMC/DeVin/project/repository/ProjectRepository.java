package UMC.DeVin.project.repository;

import UMC.DeVin.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project,Long>, ProjectRepositoryCustom {

}
