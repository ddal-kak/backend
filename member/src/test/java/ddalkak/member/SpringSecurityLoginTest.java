package ddalkak.member;

import com.google.gson.Gson;
import ddalkak.member.domain.EventStatus;
import ddalkak.member.domain.EventType;
import ddalkak.member.domain.entity.Member;
import ddalkak.member.dto.request.LoginRequest;
import ddalkak.member.dto.request.SignUpRequest;
import ddalkak.member.repository.member.DataJpaMemberRepository;
import ddalkak.member.repository.outbox.DataJpaOutboxRepository;
import ddalkak.member.repository.refreshtoken.DataJpaRefreshTokenRepository;
import ddalkak.member.repository.refreshtoken.RefreshTokenRepository;
import ddalkak.member.service.member.MemberService;
import ddalkak.member.service.outbox.OutboxService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringSecurityLoginTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private MemberService memberService;
    @Autowired private DataJpaMemberRepository dataJpaMemberRepository;
    @Autowired private DataJpaRefreshTokenRepository dataJpaRefreshTokenRepository;
    @Autowired private DataJpaOutboxRepository dataJpaOutboxRepository;
    @Autowired private RefreshTokenRepository refreshTokenRepository;
    @MockitoSpyBean
    private OutboxService outboxService;

    @AfterEach
    void cleanUp() {
        dataJpaOutboxRepository.deleteAll();
        dataJpaMemberRepository.deleteAll();
        dataJpaRefreshTokenRepository.deleteAll();
    }

    @Test
    void 로그인_성공_Refresh토큰_Outbox_저장_확인() throws Exception {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("홍길동", "test@exam.com", "password");
        Member member = memberService.signup(signUpRequest);
        LoginRequest loginRequest = new LoginRequest("test@exam.com", "password");

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(loginRequest))
        );

        //then
        // 응답 검증
        resultActions
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.AUTHORIZATION))
                .andExpect(header().string(HttpHeaders.SET_COOKIE, Matchers.containsString("refreshToken")))
                .andExpect(jsonPath("$.memberId").value(member.getMemberId()))
                .andExpect(jsonPath("$.name").value(member.getName()))
                .andExpect(jsonPath("$.email").value(member.getEmail()));
        // DB 상태 검증
        assertThat(refreshTokenRepository.findByMemberId(member.getMemberId())).isPresent();
        assertThat(dataJpaOutboxRepository.findAll()).anyMatch(outbox -> outbox.getType() == EventType.LOGIN && outbox.getStatus() == EventStatus.READY_TO_PUBLISH);
    }

    @Test
    void 로그인_실패_이벤트_리스너들의_로컬_트랜잭션_실패() throws Exception {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("홍길동", "test@exam.com", "password");
        Member member = memberService.signup(signUpRequest);
        LoginRequest loginRequest = new LoginRequest("test@exam.com", "password");
        doThrow(new RuntimeException("DB Error"))
                .when(outboxService).saveLoginEvent(any());

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(loginRequest))
        );

        //then
        // 응답 검증
        resultActions.andExpect(status().isInternalServerError());
        // DB 롤백 확인
        assertThat(refreshTokenRepository.findByMemberId(member.getMemberId())).isEmpty();
        assertThat(dataJpaOutboxRepository.count()).isZero();
    }



    @ParameterizedTest
    @MethodSource("provideLoginRequest")
    void 로그인_실패_올바르지_않은_아이디_혹은_비밀번호(SignUpRequest signUpRequest, LoginRequest loginRequest) throws Exception {
        //given
        memberService.signup(signUpRequest);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(loginRequest))
        );

        //then
        resultActions.andExpect(status().isUnauthorized());
    }

    static Stream<Arguments> provideLoginRequest() {
        return Stream.of(
                Arguments.of(new SignUpRequest("ex", "test1@exam.com", "password"),
                        new LoginRequest("test1@exam.com", "invalid_password")),
                Arguments.of(new SignUpRequest("ex", "test2@exam.com", "password"),
                        new LoginRequest("invalid@exam.com", "password"))
        );
    }
}
