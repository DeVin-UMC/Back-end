package UMC.DeVin.project;
;
import UMC.DeVin.project.entity.Project;
import UMC.DeVin.project.entity.ProjectPlatform;
import UMC.DeVin.project.entity.ProjectRecruitment;
import UMC.DeVin.project.entity.ProjectRegion;
import UMC.DeVin.project.entity.level.ProgramLevel;
import UMC.DeVin.project.repository.ProjectPlatformRepository;
import UMC.DeVin.project.repository.ProjectRecruitmentRepository;
import UMC.DeVin.project.repository.ProjectRegionRepository;
import UMC.DeVin.project.repository.ProjectRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class) // 스프링 부트와 Junit 사이 연결
public class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectPlatformRepository projectPlatformRepository;
    @Autowired
    ProjectRecruitmentRepository projectRecruitmentRepository;
    @Autowired
    ProjectRegionRepository projectRegionRepository;

    @After // 단위 테스트가 끝날 때마다 내용 지워줌
    public void cleanup(){
        projectRepository.deleteAll();
    }


    @Test
    public void 프로젝트_등록(){
        //given
        String title = "test title";
        String des = "test des";
        String img = "test img";

        projectRepository.save(Project.builder()
                         .title(title)
                         .des(des)
                         .programLevel(ProgramLevel.ADVANCED)
                         .img(img)
                         .build());

        //when
        List<Project> projectList = projectRepository.findAll();

        //then
        Project project = projectList.get(0);
        assertThat(project.getTitle()).isEqualTo(title);
        assertThat(project.getDes()).isEqualTo(des);

    }

    @Test
    public void 프로젝트_연관관계_등록(){

        //given
        for(int i=0; i<10; i++){
            projectPlatformRepository.save(ProjectPlatform.builder()
                    .title("platTitle"+i)
                    .build());
        }

        for(int j=0; j<10; j++){
            projectRecruitmentRepository.save(ProjectRecruitment.builder()
                    .title("recruitmentTitle"+j)
                    .num(j)
                    .build());
        }

        for(int k=0; k<10; k++){
            projectRegionRepository.save(ProjectRegion.builder()
                    .title("regionTitle"+k)
                    .build());
        }

        //when
        List<ProjectPlatform> projectPlatforms = projectPlatformRepository.findAll();
        List<ProjectRecruitment> projectRecruitments = projectRecruitmentRepository.findAll();
        List<ProjectRegion> projectRegions = projectRegionRepository.findAll();

        //then
        assertThat(projectPlatforms.get(5).getTitle()).isEqualTo("platTitle5");

        assertThat(projectRecruitments.get(3).getTitle()).isEqualTo("recruitmentTitle3");
        assertThat(projectRecruitments.get(6).getNum()).isEqualTo(6);

        assertThat(projectRegions.get(9).getTitle()).isEqualTo("regionTitle9");

    }


}
