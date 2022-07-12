package UMC.DeVin.member.service;

import UMC.DeVin.member.Member;
import UMC.DeVin.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository userRepository;

    public Member getUser(String userId) {
        return userRepository.findByEmail(userId).get();
    }
}

