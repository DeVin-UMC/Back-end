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

@Entity(name = "RECOMMEND_ANSWER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartAnswer extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "rec_ans_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mbr_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ans_id", nullable = false)
    private Answer answer;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "rec_type")
    private Type type; // like, unlike

    public static HeartAnswer createLikeAnswer(Member member, Answer answer){
        HeartAnswer likeAnswer = new HeartAnswer();
        likeAnswer.member = member;
        likeAnswer.answer = answer;
        likeAnswer.type = Type.LIKE;

        return likeAnswer;
    }

    public static HeartAnswer createUnlikeAnswer(Member member, Answer answer){
        HeartAnswer unlikeAnswer = new HeartAnswer();
        unlikeAnswer.member = member;
        unlikeAnswer.answer = answer;
        unlikeAnswer.type = Type.UNLIKE;

        return unlikeAnswer;
    }

    public void unlike(){
        this.type = Type.UNLIKE;
    }

    public void like(){
        this.type = Type.LIKE;
    }

}
