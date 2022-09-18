package UMC.DeVin.member.service;

import UMC.DeVin.member.Member;
import UMC.DeVin.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMember(String email) {

        return memberRepository.findByEmail(email).isEmpty() ? null : memberRepository.findByEmail(email).get();

    }
}

