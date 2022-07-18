package UMC.DeVin.config.oauth.handler;

import UMC.DeVin.auth.IpAddressUtil;
import UMC.DeVin.auth.MemberRefreshToken;
import UMC.DeVin.auth.OAuthLoginUserUtil;
import UMC.DeVin.auth.repository.MemberRefreshTokenRepository;
import UMC.DeVin.common.base.BaseException;
import UMC.DeVin.common.base.BaseResponse;
import UMC.DeVin.common.base.BaseResponseStatus;
import UMC.DeVin.config.oauth.token.AuthTokenProvider;
import UMC.DeVin.member.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

    private final HandlerExceptionResolver handlerExceptionResolver;

    private static final BaseResponse<Object> exception  =
            new BaseResponse<>(BaseResponseStatus.WRONG_ACCESS);


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        //response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        //handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);

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

    }
}