package UMC.DeVin.heart.entity;

import UMC.DeVin.common.base.BaseEntity;
import UMC.DeVin.heart.entity.type.Type;
import UMC.DeVin.member.Member;
import UMC.DeVin.qna.entity.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "recommend_question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartQuestion extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "rec_qus_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mbr_id",nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "qus_id",nullable = false)
    private Question question;

    @Enumerated(value = EnumType.STRING)
    private Type type; // like, unlike

    public static HeartQuestion createLikeQuestion(Member member, Question question){
        HeartQuestion heartQuestion = new HeartQuestion();
        heartQuestion.member=member;
        heartQuestion.question=question;
        heartQuestion.type=Type.LIKE;

        return heartQuestion;
    }

    public static HeartQuestion createUnlikeQuestion(Member member, Question question){
        HeartQuestion heartQuestion = new HeartQuestion();
        heartQuestion.member=member;
        heartQuestion.question=question;
        heartQuestion.type=Type.UNLIKE;

        return heartQuestion;
    }

    public void unlike(){
        this.type = Type.UNLIKE;
    }

    public void like() {
        this.type = Type.LIKE;
    }
}
