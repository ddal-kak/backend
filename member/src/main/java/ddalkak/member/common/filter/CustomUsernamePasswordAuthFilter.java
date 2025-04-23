package ddalkak.member.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.member.common.exception.TxFailureException;
import ddalkak.member.dto.request.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class CustomUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomUsernamePasswordAuthFilter() {
        super.setFilterProcessesUrl("/login");
    }

    /**
     * 인증 성공 시 상위 클래스의 현재 메서드에서 Custom Authentication Success Handler를 호출, 하지만 Success Handler 내부에서 발행한 이벤트로 인해
     * 리스너들의 로컬 트랜잭션이 실패해 예외가 발생할 수 있음. 이 경우 Success Handler의 트랜잭션 AOP 프록시까지 전파됨. -> Failure 처리가 필요.
     * 따라서 Success Handler를 호출하는 메서드를 오버라이드해 예외를 탐지하면 Failure Handler를 호출한다.
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        try {
            super.successfulAuthentication(request, response, chain, authResult);
        } catch (Exception e) {
            getFailureHandler().onAuthenticationFailure(
                    request,
                    response,
                    new TxFailureException("로그인 처리 중 오류가 발생했습니다.", e)
            );
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try (InputStream inputStream = request.getInputStream()) {
            LoginRequest loginRequest = objectMapper.readValue(inputStream, LoginRequest.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
