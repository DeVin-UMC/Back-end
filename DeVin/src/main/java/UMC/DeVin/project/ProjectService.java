package UMC.DeVin.project;

import UMC.DeVin.member.Member;
import UMC.DeVin.project.dto.*;
import UMC.DeVin.project.entity.Project;
import UMC.DeVin.project.entity.ProjectPlatform;
import UMC.DeVin.project.entity.ProjectRecruitment;
import UMC.DeVin.project.entity.ProjectRegion;
import UMC.DeVin.project.entity.level.ProgramLevel;
import UMC.DeVin.project.repository.ProjectPlatformRepository;
import UMC.DeVin.project.repository.ProjectRecruitmentRepository;
import UMC.DeVin.project.repository.ProjectRegionRepository;
import UMC.DeVin.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectPlatformRepository projectPlatformRepository;
    private final ProjectRecruitmentRepository projectRecruitmentRepository;
    private final ProjectRegionRepository projectRegionRepository;

    public PostProjectResDto createProject(PostProjectReqDto dto, Member member) {

        // 게시글 생성
        Project project = Project.createProject(dto,member);
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

//    @Transactional(readOnly = true)
//    public List<GetProjectDto> findAll(ProjectSearchCondition condition, Pageable pageable){
//
//        List<GetProjectDto> getProjectDtoList = new ArrayList<>();
//        List<Project> projectList;
//
//        if(condition.getLevel().isEmpty()) {
//            projectList = projectRepository.findAll(pageable).getContent();
//        }
//        else {
//            projectList = projectRepository.findByProgramLevel(pageable, ProgramLevel.valueOf(condition.getLevel())).getContent();
//        }
//
//
//        for(Project project : projectList){
//
//            List<String> platformList;
//            if(condition.getPlatform().isEmpty()){
//                platformList = projectPlatformRepository.findByProject(project).stream().map(ProjectPlatform::getTitle).collect(Collectors.toList());
//            }else{
//                platformList = projectPlatformRepository.findByProjectAndTitle(project, condition.getPlatform()).stream().map(ProjectPlatform::getTitle).collect(Collectors.toList());
//            }
//
//            List<String> regionList;
//            if(condition.getRegion().isEmpty()){
//                regionList = projectRegionRepository.findByProject(project).stream().map(ProjectRegion::getTitle).collect(Collectors.toList());
//            }else{
//                regionList = projectRegionRepository.findByProjectAndTitle(project, condition.getPlatform()).stream().map(ProjectRegion::getTitle).collect(Collectors.toList());
//            }
//
//
//            getProjectDtoList.add(GetProjectDto.builder()
//                    .title(project.getTitle())
//                    .content(project.getDes())
//                    .img(project.getImg())
//                    .programLevel(String.valueOf(project.getProgramLevel()))
//                    .platform(platformList)
//                    .region(regionList)
//                    .position(projectRecruitmentRepository.findByProject(project).stream().map(ProjectRecruitment::getTitle).collect(Collectors.toList()))
//                    .build());
//        }
//        return getProjectDtoList;
//
//    }


}


