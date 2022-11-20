package UMC.DeVin.project;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.file.FileUploadUtil;
import UMC.DeVin.member.Member;
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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectPlatformRepository projectPlatformRepository;
    private final ProjectRecruitmentRepository projectRecruitmentRepository;
    private final ProjectRegionRepository projectRegionRepository;
    private final FileUploadUtil fileUploadUtil;

    public PostProjectResDto createProject(PostProjectReqDto dto, Member member, MultipartFile file) throws BaseException {

        // 이미지 업로드
        String imgUrl = fileUploadUtil.uploadFileV1("project", file);
        
        // 게시글 생성
        Project project = Project.createProject(dto,member,imgUrl);
        projectRepository.save(project);

        // 플랫폼 생성
        if (dto.getPlatformList() != null) {
            for (PlatformDto platform : dto.getPlatformList()) {
                projectPlatformRepository.save(ProjectPlatform.createProjectPlatform(project, platform.getTitle()));
            }
        }

        // 모집 인원 생성
        if (dto.getRecruitmentList() != null) {
            for (RecruitmentDto recruitment : dto.getRecruitmentList()) {
                projectRecruitmentRepository.save(ProjectRecruitment.createRecruitment(project, recruitment.getTitle(), recruitment.getLanguage(), recruitment.getNum()));
            }
        }

        // 지역 생성
        if (dto.getRegionList() != null) {
            for (RegionDto region : dto.getRegionList()) {
                projectRegionRepository.save(ProjectRegion.createRegion(project, region.getTitle()));
            }
        }

        return new PostProjectResDto(project);
    }

    @Transactional(readOnly = true)
    public Page<ProjectRes> findPage(ProjectSearchCondition condition, Pageable pageable) {
        return projectRepository.findPage(condition, pageable);
    }

    @Transactional(readOnly = true)
    public List<ProjectRes> search(String keyword, Pageable pageable) {
        return projectRepository.findByKeyword(keyword, pageable);
    }

    
}


