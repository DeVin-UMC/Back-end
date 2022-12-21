package UMC.DeVin.member.service;

import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.config.oauth.entity.ProviderType;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.dto.MemberJoinReq;
import UMC.DeVin.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static UMC.DeVin.config.oauth.entity.ProviderType.GOOGLE;
import static UMC.DeVin.member.Member.createMember;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMember(String email) {

        return memberRepository.findByEmail(email).isEmpty() ? null : memberRepository.findByEmail(email).get();

    }

    public void join(MemberJoinReq memberJoinReq) throws BaseException {
        if (memberRepository.findByEmail(memberJoinReq.getEmail()).isPresent()) {
            throw new BaseException(BaseResponseStatus.ALREADY_JOIN_BEFORE);
        }

        memberRepository.save(createMember(memberJoinReq.getName(), memberJoinReq.getEmail(),
                memberJoinReq.getPictureUrl(), GOOGLE));
    }

}

