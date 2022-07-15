package UMC.DeVin.project;

import UMC.DeVin.project.dto.*;
import UMC.DeVin.project.entity.Project;
import UMC.DeVin.project.entity.ProjectPlatform;
import UMC.DeVin.project.entity.ProjectRecruitment;
import UMC.DeVin.project.entity.ProjectRegion;
import UMC.DeVin.project.repository.ProjectPlatformRepository;
import UMC.DeVin.project.repository.ProjectRecruitmentRepository;
import UMC.DeVin.project.repository.ProjectRegionRepository;
import UMC.DeVin.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectPlatformRepository projectPlatformRepository;
    private final ProjectRecruitmentRepository projectRecruitmentRepository;
    private final ProjectRegionRepository projectRegionRepository;

    @Transactional
    public PostProjectResDto createProject(PostProjectReqDto dto){

        // 게시글
        Project project = new Project(dto);
        projectRepository.save(project);

        // 플랫폼
        for(ProjectPlatform platform : project.getProjectPlatforms()){
            platform.setProject(project);
            projectPlatformRepository.save(platform);
        }

        // 모집 인원
        for(ProjectRecruitment recruitment : project.getProjectRecruitments()){
            recruitment.setProject(project);
            projectRecruitmentRepository.save(recruitment);
        }

        // 지역
        for(ProjectRegion region : project.getProjectRegions()){
            region.setProject(project);
            projectRegionRepository.save(region);
        }

        return new PostProjectResDto(project);
    }

}


