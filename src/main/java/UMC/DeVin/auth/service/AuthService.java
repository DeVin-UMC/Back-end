package UMC.DeVin.auth.service;

import UMC.DeVin.auth.dto.GoogleLoginResDTO;
import UMC.DeVin.auth.dto.GoogleUserDTO;
import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.config.oauth.token.AuthToken;
import UMC.DeVin.config.oauth.token.AuthTokenProvider;
import UMC.DeVin.config.properties.AppProperties;
import UMC.DeVin.member.repository.MemberRepository;
import UMC.DeVin.member.role.MemberRole;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;

import static UMC.DeVin.member.role.MemberRole.USER;

/**
 * 클라이언트로부터 넘어온 Google 로그인 정보를 파싱하는 클래스입니다.
 * @author CHO Min Ho
 * @version 1.0.0
 */
@Service
public class AuthService {

    private final AuthTokenProvider authTokenProvider;
    private final GoogleIdTokenVerifier verifier;
    private final MemberRepository memberRepository;
    private final AppProperties appProperties;

    public AuthService(@Value("${app.google.client.id}") String googleClientId, AuthTokenProvider authTokenProvider,
                       MemberRepository memberRepository, AppProperties appProperties) {
        this.authTokenProvider = authTokenProvider;
        this.memberRepository = memberRepository;
        this.appProperties = appProperties;

        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(googleClientId))
                .build();
    }

    /**
     * 클라이언트로부터 credential을 넘겨받았을 때 이미 있는 사용자이면 로그인을, 없는 사용자면 회원가입을 하도록 응답을 내려줍니다.
     * @param credential 클라이언트로부터 넘어온 credential 정보
     * @return 로그인을 할지, 회원가입을 할지 여부를 포함한 사용자 정보
     * @throws BaseException credential 파싱 실패 시
     */
    public GoogleLoginResDTO googleLogin(String credential) throws BaseException {
        GoogleUserDTO googleUserDTO = verifyIdToken(credential);

        // access token 발급
        Date now = new Date();
        AuthToken accessToken = authTokenProvider.createAuthToken(
                googleUserDTO.getEmail(),
                USER.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        // 로그인 케이스
        if (memberRepository.findByEmail(googleUserDTO.getEmail()).isPresent()) {
            return new GoogleLoginResDTO(googleUserDTO, true, accessToken.getToken());
        }

        // 회원가입 케이스
        return new GoogleLoginResDTO(googleUserDTO, false, accessToken.getToken());

    }


    /**
     * 회원 가입된 사용자의 경우 클라이언트로부터 넘어온 credential 문자열을 사용자 정보로 파싱하는 메서드입니다.
     * @param credential 클라이언트로부터 넘어온 credential 정보
     * @return 해당 credential에 해당하는 사용자 정보
     */
    private GoogleUserDTO verifyIdToken(String credential) throws BaseException {
        try {
            GoogleIdToken idToken = verifier.verify(credential);
            if (idToken == null) {
                throw new BaseException(BaseResponseStatus.INVALID_GOOGLE_CREDENTIAL);
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String pictureUrl = (String) payload.get("picture");
            String name = payload.get("given_name") + (String) payload.get("family_name");

            return new GoogleUserDTO(email, pictureUrl, name);

        } catch (GeneralSecurityException e) {
            throw new BaseException(BaseResponseStatus.INVALID_GOOGLE_CREDENTIAL);
        } catch (IOException e) {
            throw new BaseException(BaseResponseStatus.UNKNOWN_SERVER_EXCEPTION);
        }

    }

}
