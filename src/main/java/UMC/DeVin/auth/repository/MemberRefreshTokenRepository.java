package UMC.DeVin.auth.repository;

import UMC.DeVin.auth.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {

    MemberRefreshToken findByUserId(String userId);
    MemberRefreshToken findByUserIdAndRefreshToken(String userId, String refreshToken);
    Optional<MemberRefreshToken> findByRefreshToken(String refreshToken);
    List<MemberRefreshToken> findByMemberId(Long memberId);

    Optional<MemberRefreshToken> findByMemberIdAndUserIpAddress(Long memberId, String ipAddress);

}