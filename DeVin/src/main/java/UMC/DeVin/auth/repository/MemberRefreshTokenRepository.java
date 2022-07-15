package UMC.DeVin.auth.repository;

import UMC.DeVin.auth.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {

    MemberRefreshToken findByUserId(String userId);
    MemberRefreshToken findByUserIdAndRefreshToken(String userId, String refreshToken);
    Optional<MemberRefreshToken> findByRefreshToken(String refreshToken);

}