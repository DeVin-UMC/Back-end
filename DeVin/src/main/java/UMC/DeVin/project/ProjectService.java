package UMC.DeVin.project;

import UMC.DeVin.member.Member;
import UMC.DeVin.member.repository.MemberRepository;
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
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectPlatformRepository projectPlatformRepository;
    private final ProjectRecruitmentRepository projectRecruitmentRepository;
    private final ProjectRegionRepository projectRegionRepository;
    private final MemberRepository memberRepository;

    public PostProjectResDto createProject(PostProjectReqDto dto) {

        // 작성자
        Member findMember = memberRepository.findById(dto.getMemberId()).get();

        // 게시글
        Project project = Project.createProject(dto,findMember);
        projectRepository.save(project);

        // 플랫폼 생성
        if (dto.getPlatformList() != null) {
            for (PlatformDto platform : dto.getPlatformList()) {
                projectPlatformRepository.save(ProjectPlatform.createProjectPlatform(project, platform.getTitle()));
            }
        }

        // 모집 인원
        if (dto.getRecruitmentList() != null) {
            for (RecruitmentDto recruitment : dto.getRecruitmentList()) {
                projectRecruitmentRepository.save(ProjectRecruitment.createRecruitment(project, recruitment.getTitle(), recruitment.getLanguage(), recruitment.getNum()));
            }
        }

        // 지역
        if (dto.getRegionList() != null) {
            for (RegionDto region : dto.getRegionList()) {
                projectRegionRepository.save(ProjectRegion.createRegion(project, region.getTitle()));
            }
        }

        return new PostProjectResDto(project);
    }

}


