package ddalkak.member.common.filter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.member.common.exception.TxFailureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
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
            throw new IllegalStateException("body를 작성할 수 없습니다.");
        }
    }

    private void setDBTransactionFailureResponse(HttpServletResponse response, AuthenticationException ex) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        try {
            objectMapper.writeValue(response.getWriter(), ex.getMessage());
        } catch (IOException e) {
            throw new IllegalStateException("body를 작성할 수 없습니다.");
        }
    }


}
