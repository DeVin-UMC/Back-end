package UMC.DeVin.qna;

import UMC.DeVin.heart.entity.type.Type;
import UMC.DeVin.heart.repository.HeartQuestionRepository;
import UMC.DeVin.member.Member;
import UMC.DeVin.qna.dto.*;
import UMC.DeVin.qna.entity.Answer;
import UMC.DeVin.qna.entity.Question;
import UMC.DeVin.qna.entity.QuestionTag;
import UMC.DeVin.qna.entity.select.Select;
import UMC.DeVin.qna.repository.AnswerRepository;
import UMC.DeVin.qna.repository.QuestionRepository;
import UMC.DeVin.qna.repository.QuestionTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QnaService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final QuestionTagRepository questionTagRepository;
    private final HeartQuestionRepository heartQuestionRepository;


    public PostQuestionRes createQuestion(PostQuestionReq dto, Member writer){
        System.out.println(writer);
        System.out.println(1);
        Question question = Question.createQuestion(dto, writer);
        System.out.println(question);
        questionRepository.save(question);

        if(!dto.getTagList().isEmpty()){
            for(PostTagReq TagTitle : dto.getTagList()) {
                questionTagRepository.save(QuestionTag.createTag(question, TagTitle.getTitle()));
            }
        }

        return new PostQuestionRes(question.getId());
    }

    public PostAnswerRes createAnswer(PostAnswerReq dto, Member writer){

        Question findQuestion = questionRepository.findById(dto.getQuestionId()).get();
        Answer answer = Answer.createAnswer(dto, findQuestion, writer);
        answerRepository.save(answer);

        return new PostAnswerRes(answer.getId());
    }

    public String selectAnswer(Long id) {
        Answer findAnswer = answerRepository.findById(id).get();

        if(findAnswer.getSelect() != Select.TRUE) {
            findAnswer.selectAnswer();
            return "답변 채택";

        }else {
            findAnswer.unselectAnswer();
            return "답변 채택 취소";
        }

    }

    public List<GetQnaDto> pageQna(Pageable pageable) {

        List<GetQnaDto> qnaDtoList = new ArrayList<>();
        List<Question> questionList = questionRepository.findAll(pageable).getContent();

        for(Question question : questionList){
            qnaDtoList.add(
                    GetQnaDto.builder()
                            .title(question.getTitle())
                            .content(question.getContent())
                            .writer(question.getWriter().getName())
                            .countAnswer(answerRepository.findByQuestion(question,pageable).size())
                            .countLike(heartQuestionRepository.findByQuestionAndType(question, Type.LIKE).size())
                            .countUnlike(heartQuestionRepository.findByQuestionAndType(question, Type.UNLIKE).size())
                            .tags(questionTagRepository.findByQuestion(question, pageable).stream().map(QuestionTag::getTitle).collect(Collectors.toList()))
                            .build());

        }
        return qnaDtoList;

    }

    public List<GetQnaDto> searchQna(String keyword, Pageable pageable) {
        List<GetQnaDto> qnaDtoList = new ArrayList<>();
        List<Question> questionList =  new ArrayList<>();
        if(!questionRepository.findByTitleContaining(keyword, pageable).getContent().isEmpty()){
            questionList = questionRepository.findByTitleContaining(keyword, pageable).getContent();
        } else if (!questionRepository.findByContentContaining(keyword, pageable).getContent().isEmpty()){
            questionList = questionRepository.findByContentContaining(keyword, pageable).getContent();
        }

        for(Question question : questionList){
            qnaDtoList.add(
                    GetQnaDto.builder()
                            .title(question.getTitle())
                            .content(question.getContent())
                            .writer(question.getWriter().getName())
                            .countAnswer(answerRepository.findByQuestion(question,pageable).size())
                            .countLike(heartQuestionRepository.findByQuestionAndType(question, Type.LIKE).size())
                            .countUnlike(heartQuestionRepository.findByQuestionAndType(question, Type.UNLIKE).size())
                            .tags(questionTagRepository.findByQuestion(question, pageable).stream().map(QuestionTag::getTitle).collect(Collectors.toList()))
                            .like(heartQuestionRepository.findByQuestionAndType(question,Type.LIKE).isEmpty() ? false : true)
                            .unlike(heartQuestionRepository.findByQuestionAndType(question,Type.UNLIKE).isEmpty() ? false : true)
                            .commented(answerRepository.findByQuestion(question,pageable).isEmpty() ? false : true)
                            .build());

        }
        return qnaDtoList;
    }
}
