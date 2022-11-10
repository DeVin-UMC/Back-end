package UMC.DeVin.test;

import UMC.DeVin.config.oauth.entity.ProviderType;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.repository.MemberRepository;
import UMC.DeVin.project.ProjectService;
import UMC.DeVin.project.dto.PlatformDto;
import UMC.DeVin.project.dto.PostProjectReqDto;
import UMC.DeVin.project.dto.RecruitmentDto;
import UMC.DeVin.project.dto.RegionDto;
import UMC.DeVin.qna.QnaService;
import UMC.DeVin.qna.dto.PostAnswerReq;
import UMC.DeVin.qna.dto.PostQuestionReq;
import UMC.DeVin.qna.dto.PostQuestionRes;
import UMC.DeVin.qna.dto.PostTagReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static UMC.DeVin.config.oauth.entity.ProviderType.GOOGLE;
import static UMC.DeVin.member.Member.createMember;

/**
 * Test 데이터 삽입 코드입니다.
 * 실제 서비스 배포시 Profile 설정 예정
 */
//@Profile("dev")
@Component
@RequiredArgsConstructor
@Slf4j
public class TestData {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitService {
        @PersistenceContext
        private EntityManager em;

        private final ProjectService projectService;
        private final MemberRepository memberRepository;
        private final QnaService qnaService;

        @Transactional
        public void init() {

            // 1. Test Member 데이터 삽입
            Member member1 = addTestMemberData("조민호", "abcde@gmail.com",
                    "https://lh3.googleusercontent.com/a/ALm5wu1VUV8hWTMH5wJbVoz6G26aqgN1GmlDMPHPLrd6rA=s96-c",
                    GOOGLE, "닉네임1");
            Member member2 = addTestMemberData("홍길동", "bcded@gmail.com",
                    "https://lh3.googleusercontent.com/a/ALm5wu2pFRYWr0JViHFpE9rjV46yzchMSowhQFAyMPSl=s96-c",
                    GOOGLE, "닉네임2");
            Member member3 = addTestMemberData("이름", "aaaaae@gmail.com",
                    "https://lh3.googleusercontent.com/a/ALm5wu3udnowJSDlxl1F0gW6op0k_VRAJr_WlSWQ6V2z=s96-c",
                    GOOGLE, "닉네임3");
            Member member4 = addTestMemberData("이름2", "bbbbb@gmail.com",
                    "https://lh3.googleusercontent.com/a/ALm5wu1VUV8hWTMH5wJbVoz6G26aqgN1GmlDMPHPLrd6rA=s96-c",
                    GOOGLE, "닉네임4");
            Member member5 = addTestMemberData("이름3", "cccc@gmail.com",
                    "https://lh3.googleusercontent.com/a/ALm5wu1VUV8hWTMH5wJbVoz6G26aqgN1GmlDMPHPLrd6rA=s96-c",
                    GOOGLE, "닉네임5");

            // 2. Test Project 데이터 삽입
            addTestProjectData("쇼핑몰 프로젝트 팀원 모집합니다.", "같이 쇼핑몰 만들어요", "web", member1);
            addTestProjectData("스프링 프로젝트 팀원 모집합니다.", "같이 쇼핑몰 만들어요", "app", member1);
            addTestProjectData("리액트 프로젝트 팀원 모집합니다.", "같이 쇼핑몰 만들어요", "game", member1);
            addTestProjectData("쇼핑몰 프로젝트 팀원 모집해요", "같이 쇼핑몰 만들어요", "desktop-app", member2);
            addTestProjectData("쇼핑몰 만들어요", "같이 쇼핑몰 만들어요", "web", member3);
            addTestProjectData("프로젝트 모집 제목1", "같이 쇼핑몰 만들어요", "app", member4);
            addTestProjectData("프로젝트 모집 제목2", "같이 쇼핑몰 만들어요", "web", member5);
            addTestProjectData("프로젝트 모집 제목3", "같이 쇼핑몰 만들어요", "web", member5);
            addTestProjectData("프로젝트 모집 제목4", "같이 쇼핑몰 만들어요", "web", member2);
            addTestProjectData("프로젝트 모집 제목5", "같이 쇼핑몰 만들어요", "web", member2);

            // 3. Test Study 데이터 삽입



            // 4. Test Q&A 데이터 삽입
            PostQuestionRes postQuestionRes1 = addTestQuestionData("java", "서블렛이 무엇인가요?");
            PostQuestionRes postQuestionRes2 = addTestQuestionData("Python", "파이썬은 무엇인가요?");
            PostQuestionRes postQuestionRes3 = addTestQuestionData("Spring", "백준 2929번 질문 있습니다!");
            PostQuestionRes postQuestionRes4 = addTestQuestionData("java", "서블렛이 무엇인가요?");
            PostQuestionRes postQuestionRes5 = addTestQuestionData("java", "서블렛이 무엇인가요?");

            addTestAnswerData(postQuestionRes1.getQuestionId());
            addTestAnswerData(postQuestionRes1.getQuestionId());
            addTestAnswerData(postQuestionRes2.getQuestionId());
            addTestAnswerData(postQuestionRes2.getQuestionId());
            addTestAnswerData(postQuestionRes3.getQuestionId());
            addTestAnswerData(postQuestionRes4.getQuestionId());
            addTestAnswerData(postQuestionRes5.getQuestionId());



        }

        private Member addTestMemberData(String name, String email, String imageUrl, ProviderType type, String nickname) {
            Member member = createMember(name, email, imageUrl, type);
            member.updateAdditionalInfo(nickname);
            return memberRepository.save(member);
        }

        private void addTestProjectData(String title, String description, String platform, Member member) {
            List<PlatformDto> platformList = new ArrayList<>();
            platformList.add(new PlatformDto(platform));

            List<RecruitmentDto> recruitmentList = new ArrayList<>();
            recruitmentList.add(new RecruitmentDto("front-end", "react", 5));
            recruitmentList.add(new RecruitmentDto("back-end", "spring", 5));

            List<RegionDto> regionList = new ArrayList<>();
            regionList.add(new RegionDto("서울"));
            regionList.add(new RegionDto("인천"));


            projectService.createProject(new PostProjectReqDto(title, description,
                    "BEGINNER", "test img", platformList, recruitmentList,  regionList), member);
        }

        private PostQuestionRes addTestQuestionData(String tag, String title) {
            List<PostTagReq> tagList = new ArrayList<>();
            tagList.add(new PostTagReq(tag));
            tagList.add(new PostTagReq("C++"));

            return qnaService.createQuestion(new PostQuestionReq(title,
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                    tagList));
        }

        private void addTestAnswerData(Long questionId) {
            qnaService.createAnswer(new PostAnswerReq(questionId,
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."));
        }
    }
}
