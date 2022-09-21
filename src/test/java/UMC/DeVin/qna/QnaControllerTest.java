package UMC.DeVin.qna;

import UMC.DeVin.config.oauth.entity.ProviderType;
import UMC.DeVin.member.Member;
import UMC.DeVin.project.dto.PlatformDto;
import UMC.DeVin.project.dto.PostProjectReqDto;
import UMC.DeVin.project.dto.RecruitmentDto;
import UMC.DeVin.project.dto.RegionDto;
import UMC.DeVin.project.entity.Project;
import UMC.DeVin.project.repository.ProjectRepository;
import UMC.DeVin.qna.dto.PostAnswerReq;
import UMC.DeVin.qna.dto.PostQuestionReq;
import UMC.DeVin.qna.dto.PostTagReq;
import UMC.DeVin.qna.entity.Answer;
import UMC.DeVin.qna.entity.Question;
import UMC.DeVin.qna.entity.QuestionTag;
import UMC.DeVin.qna.repository.AnswerRepository;
import UMC.DeVin.qna.repository.QuestionRepository;
import UMC.DeVin.qna.repository.QuestionTagRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QnaControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionTagRepository questionTagRepository;
    @Autowired
    private AnswerRepository answerRepository;
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
        questionRepository.deleteAll();
        questionTagRepository.deleteAll();
        answerRepository.deleteAll();
    }

    @Test
    @WithMockUser
    public void qna_검색() throws Exception {
        //given
        List<PostTagReq> tagList = new ArrayList<>();
        tagList.add(new PostTagReq("푸하하 질문 태그입니다"));

        PostQuestionReq questionDto = new PostQuestionReq("질문 제목", "질문 내용", tagList);
        Question question = Question.createQuestion(questionDto);

        questionRepository.save(question);
        questionTagRepository.save(QuestionTag.createTag(question,tagList.get(0).getTitle()));

        PostAnswerReq answerDto = new PostAnswerReq(question.getId(), "답변입니다.");
        answerRepository.save(Answer.createAnswer(answerDto,question));

        //when
        mvc.perform(get("/qna/search")
                        .param("keyword","푸하하"))
                .andExpect(status().isOk())
                .andDo(print());

        //then
    }



}
