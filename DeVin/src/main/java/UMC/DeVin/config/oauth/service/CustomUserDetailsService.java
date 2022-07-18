package UMC.DeVin.config.oauth.service;

import UMC.DeVin.config.oauth.entity.UserPrincipal;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> findMember = userRepository.findByEmail(username);
        if (findMember.isEmpty()) {
            throw new UsernameNotFoundException("Can not find username.");
        }
        return UserPrincipal.create(findMember.get());
    }
}

