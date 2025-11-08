package ddalkak.member.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.member.common.exception.TxFailureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException ex) {
        if (ex instanceof TxFailureException) {
            setDBTransactionFailureResponse(response, ex);
        } else {
            setAuthenticationFailureResponse(response, ex);
        }
    }

    private void setAuthenticationFailureResponse(HttpServletResponse response, AuthenticationException ex) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        try {
            objectMapper.writeValue(response.getWriter(), ex.getMessage());
        } catch (IOException e) {
            log.error("인증 실패 응답 작성 중 I/O 오류 발생", e);
        }
    }

    private void setDBTransactionFailureResponse(HttpServletResponse response, AuthenticationException ex) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        try {
            objectMapper.writeValue(response.getWriter(), ex.getMessage());
        } catch (IOException e) {
            log.error("인증 실패 응답 작성 중 I/O 오류 발생", e);
        }
    }
}
