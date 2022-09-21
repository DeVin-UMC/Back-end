package UMC.DeVin.project;

import UMC.DeVin.config.oauth.entity.ProviderType;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.repository.MemberRepository;
import UMC.DeVin.project.dto.PlatformDto;
import UMC.DeVin.project.dto.PostProjectReqDto;
import UMC.DeVin.project.dto.RecruitmentDto;
import UMC.DeVin.project.dto.RegionDto;
import UMC.DeVin.project.entity.Project;
import UMC.DeVin.project.entity.ProjectPlatform;
import UMC.DeVin.project.repository.ProjectPlatformRepository;
import UMC.DeVin.project.repository.ProjectRecruitmentRepository;
import UMC.DeVin.project.repository.ProjectRegionRepository;
import UMC.DeVin.project.repository.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProjectPlatformRepository projectPlatformRepository;
    @Autowired
    private ProjectRecruitmentRepository projectRecruitmentRepository;
    @Autowired
    private ProjectRegionRepository projectRegionRepository;


    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @After
    public void tearDown() throws Exception{
        projectRepository.deleteAll();
    }

//    @Test
//    @WithMockUser(username = "허영은", password = "test_password", roles = "ROLE_USER")
//    public void 프로젝트_생성() throws Exception{
//        //given
//        String title = "프로젝트 제목입니다";
//        String des = "프로젝트 내용입니다";
//        String img = "프로젝트 이미지 입니다";
//
//        List<PlatformDto> platformDtoList = new ArrayList<>();
//        platformDtoList.add(new PlatformDto("첫번재 플랫폼"));
//        platformDtoList.add(new PlatformDto("두번재 플랫폼"));
//
//        List<RecruitmentDto> recruitmentDtoList = new ArrayList<>();
//        recruitmentDtoList.add(new RecruitmentDto("프론트","리액트",2));
//        recruitmentDtoList.add(new RecruitmentDto("백","스프링부트",2));
//
//        List<RegionDto> regionDtoList = new ArrayList<>();
//        regionDtoList.add(new RegionDto("인천"));
//        regionDtoList.add(new RegionDto("서울"));
//
//        PostProjectReqDto dto = PostProjectReqDto.builder()
//                .title(title)
//                .des(des)
//                .programLevel("MIDDLE")
//                .img(img)
//                .platformList(platformDtoList)
//                .recruitmentList(recruitmentDtoList)
//                .regionList(regionDtoList)
//                .build();
//
//        String url = "http://localhost:" + port + "/projects";
//
//        //when
//        mvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        //then
//        List<Project> all = projectRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(context);
//        assertThat(all.get(0).getDes()).isEqualTo(des);
//
//        Optional<ProjectPlatform> platformList = projectPlatformRepository.findById(all.get(0).getId());
//        assertThat(platformList.get().getTitle()).contains("프론트");
//    }

    @Test
    @WithMockUser
    public void 프로젝트_검색() throws Exception {
        //given
        String title = "푸하하 검색 테스트 제목입니다";
        String des = "프로젝트 내용입니다";
        String img = "프로젝트 이미지 입니다";

        List<PlatformDto> platformDtoList = new ArrayList<>();
        platformDtoList.add(new PlatformDto("첫번재 플랫폼"));
        platformDtoList.add(new PlatformDto("두번재 플랫폼"));

        List<RecruitmentDto> recruitmentDtoList = new ArrayList<>();
        recruitmentDtoList.add(new RecruitmentDto("프론트","리액트",2));
        recruitmentDtoList.add(new RecruitmentDto("백","스프링부트",2));

        List<RegionDto> regionDtoList = new ArrayList<>();
        regionDtoList.add(new RegionDto("인천"));
        regionDtoList.add(new RegionDto("서울"));

        PostProjectReqDto dto = PostProjectReqDto.builder()
                .title(title)
                .des(des)
                .programLevel("MIDDLE")
                .img(img)
                .platformList(platformDtoList)
                .recruitmentList(recruitmentDtoList)
                .regionList(regionDtoList)
                .build();

        projectRepository.save(Project.createProject(dto,
                memberRepository.save(Member.createMember("허영은","1219215@inha.edu","img", ProviderType.LOCAL))));

        //when
        mvc.perform(get("/projects/search")
                        .param("keyword","푸하하"))
                .andExpect(status().isOk())
                .andDo(print());

        //then
    }
}