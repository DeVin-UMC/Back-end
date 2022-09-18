package UMC.DeVin.config.oauth.exception;

import UMC.DeVin.auth.IpAddressUtil;
import UMC.DeVin.auth.MemberRefreshToken;
import UMC.DeVin.auth.OAuthLoginUserUtil;
import UMC.DeVin.auth.repository.MemberRefreshTokenRepository;
import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.config.oauth.utils.CookieUtil;
import UMC.DeVin.member.Member;
import UMC.DeVin.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

import static UMC.DeVin.config.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final BaseResponse<Object> exception  =
            new BaseResponse<>(BaseResponseStatus.WRONG_ACCESS);

    private final OAuthLoginUserUtil oAuthLoginUserUtil;
    private final MemberRefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;


    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        authException.printStackTrace();
        log.error("Responding with unauthorized error. Message := {}", authException.getMessage());
        log.error("Current URI : {}", request.getRequestURI());

        // 잘못 생성된 refresh token 삭제
        try {
            Member findMember = oAuthLoginUserUtil.getLoginMemberWithToken(request.getParameter("token"));

            // 요청이 들어온 IP 주소와 member ID를 바탕으로 token 탐색
            Optional<MemberRefreshToken> findToken = refreshTokenRepository.findByMemberIdAndUserIpAddress(findMember.getId(), IpAddressUtil.getRemoteAddr(request));

            // refresh token이 존재할 경우
            findToken.ifPresent(refreshTokenRepository::delete);
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);

            // 기존에 존재하던 회원이 아닐 경우 회원 삭제
            if (findMember.getNickname() == null) {
                memberRepository.delete(findMember);
            }
        }
        catch (Exception ignore) { }

        // put into response
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        // make JSON with HTTP status and local time
        try (OutputStream os = response.getOutputStream()) {
            // to handle LocalDateTime (Java 8)
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            objectMapper.writeValue(os, exception);
            os.flush();
        }


        /*response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                authException.getLocalizedMessage()
        );*/
    }


}

