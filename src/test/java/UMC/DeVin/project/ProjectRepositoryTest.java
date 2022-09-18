//package UMC.DeVin.project;
//import UMC.DeVin.config.oauth.entity.ProviderType;
//import UMC.DeVin.member.Member;
//import UMC.DeVin.member.repository.MemberRepository;
//import UMC.DeVin.project.dto.PlatformDto;
//import UMC.DeVin.project.dto.PostProjectReqDto;
//import UMC.DeVin.project.dto.RecruitmentDto;
//import UMC.DeVin.project.dto.RegionDto;
//import UMC.DeVin.project.entity.Project;
//import UMC.DeVin.project.entity.ProjectPlatform;
//import UMC.DeVin.project.entity.ProjectRecruitment;
//import UMC.DeVin.project.entity.ProjectRegion;
//import UMC.DeVin.project.repository.ProjectPlatformRepository;
//import UMC.DeVin.project.repository.ProjectRecruitmentRepository;
//import UMC.DeVin.project.repository.ProjectRegionRepository;
//import UMC.DeVin.project.repository.ProjectRepository;
//import org.junit.After;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.internal.util.Platform;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//@RunWith(SpringRunner.class) // 스프링 부트와 Junit 사이 연결
//public class ProjectRepositoryTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//    @Autowired
//    ProjectRepository projectRepository;
//    @Autowired
//    ProjectPlatformRepository projectPlatformRepository;
//    @Autowired
//    ProjectRecruitmentRepository projectRecruitmentRepository;
//    @Autowired
//    ProjectRegionRepository projectRegionRepository;
//
//    @After // 단위 테스트가 끝날 때마다 내용 지워줌
//    public void cleanup(){
//        projectRepository.deleteAll();
//    }
//
//    @Test
//    public void 프로젝트_등록(){
//        //given
//        String title = "test title";
//        String des = "test des";
//        String img = "test img";
//        String platformTitle = "test platform";
//        String recruitmentTitle = "test recruitmentTitle";
//        String language = "spring";
//        Integer num = 2;
//        String regionTitle = "test regionTitle";
//
//        List<PlatformDto> platformDtoList = new ArrayList<>();
//        PlatformDto platformDto = new PlatformDto(platformTitle);
//        platformDtoList.add(platformDto);
//
//        List<RecruitmentDto> recruitmentDtoList = new ArrayList<>();
//        RecruitmentDto recruitmentDto = new RecruitmentDto(recruitmentTitle,language,num);
//        recruitmentDtoList.add(recruitmentDto);
//
//        List<RegionDto> regionDtoList= new ArrayList<>();
//        RegionDto regionDto = new RegionDto(regionTitle);
//        regionDtoList.add(regionDto);
//
//        Member member = Member.createMember("name","abc123@gmail.com","image", ProviderType.GOOGLE);
//        memberRepository.save(member);
//        PostProjectReqDto dto = new PostProjectReqDto(member.getId(), title,des,"ADVANCED",img, platformDtoList, recruitmentDtoList, regionDtoList);
//
//
//        Project project1 = Project.createProject(dto, member);
//        projectRepository.save(project1);
//        projectPlatformRepository.save(ProjectPlatform.createProjectPlatform(project1,platformDto.getTitle()));
//        projectRecruitmentRepository.save(ProjectRecruitment.createRecruitment(project1,recruitmentDto.getTitle(),recruitmentDto.getLanguage(),recruitmentDto.getNum()));
//        projectRegionRepository.save(ProjectRegion.createRegion(project1,regionDto.getTitle()));
//
//        //when
//        List<Project> projectList = projectRepository.findAll();
//        List<ProjectPlatform> projectPlatformList = projectPlatformRepository.findAll();
//
//        //then
//        Project findProject = projectList.get(0);
//        assertThat(findProject.getTitle()).isEqualTo(title);
//        assertThat(findProject.getDes()).isEqualTo(des);
//        assertThat(projectPlatformList.get(0).getTitle()).isEqualTo(platformTitle);
//
//    }
//
//
//}
