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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartQuestion extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Member member;

    @ManyToOne(fetch = LAZY)
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

    public void unlike(){
        this.type = Type.UNLIKE;
    }

}
