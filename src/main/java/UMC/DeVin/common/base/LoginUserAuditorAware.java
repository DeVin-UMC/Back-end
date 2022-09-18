package UMC.DeVin.common.base;

import UMC.DeVin.auth.OAuthLoginUserUtil;
import UMC.DeVin.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*@Configuration
@EnableJpaAuditing*/
@Component
@EnableJpaAuditing
@RequiredArgsConstructor
public class LoginUserAuditorAware implements AuditorAware<Long> {

    private final OAuthLoginUserUtil oAuthLoginUserUtil;

    /**
     *
     * @return 로그인된 Member의 ID를 반환
     */
    @Override
    public Optional<Long> getCurrentAuditor() {

        try {
            Member loginMember = oAuthLoginUserUtil.getLoginMemberWithContext();
            return Optional.ofNullable(loginMember.getId());
        } catch (Exception e) {
            return Optional.empty();
        }

    }
}
