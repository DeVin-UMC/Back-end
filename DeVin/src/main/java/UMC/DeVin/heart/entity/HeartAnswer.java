package UMC.DeVin.heart.entity;

import UMC.DeVin.common.base.BaseEntity;
import UMC.DeVin.heart.entity.type.Type;
import UMC.DeVin.member.Member;
import UMC.DeVin.qna.entity.Answer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartAnswer extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Member member;

    @ManyToOne(fetch = LAZY)
    private Answer answer;

    @Enumerated(value = EnumType.STRING)
    private Type type; // like, unlike

    public static HeartAnswer createLikeAnswer(Member member, Answer answer){
        HeartAnswer likeAnswer = new HeartAnswer();
        likeAnswer.member = member;
        likeAnswer.answer = answer;
        likeAnswer.type = Type.LIKE;

        return likeAnswer;
    }

    public void unlike(){
        this.type = Type.UNLIKE;
    }

}
