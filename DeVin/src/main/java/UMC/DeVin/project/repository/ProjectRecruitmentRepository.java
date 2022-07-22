package UMC.DeVin.project.repository;

import UMC.DeVin.project.entity.Project;
import UMC.DeVin.project.entity.ProjectRecruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProjectRecruitmentRepository extends JpaRepository<ProjectRecruitment,Long> {

    List<ProjectRecruitment> findByProject(Project project);
}
