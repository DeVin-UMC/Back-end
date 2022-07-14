package UMC.DeVin.auth.repository;

import UMC.DeVin.auth.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {

    UserRefreshToken findByUserId(String userId);
    UserRefreshToken findByUserIdAndRefreshToken(String userId, String refreshToken);
    Optional<UserRefreshToken> findByRefreshToken(String refreshToken);

}