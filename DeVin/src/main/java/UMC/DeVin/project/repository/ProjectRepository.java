package UMC.DeVin.project.repository;

import UMC.DeVin.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;


public interface ProjectRepository extends JpaRepository<Project,Long>, ProjectRepositoryCustom {

    Page<Project> findAll(Pageable pageable);
}
