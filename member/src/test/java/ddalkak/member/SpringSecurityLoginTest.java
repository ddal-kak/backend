package ddalkak.member;

import com.google.gson.Gson;
import ddalkak.member.domain.Member;
import ddalkak.member.dto.request.LoginRequest;
import ddalkak.member.dto.request.SignUpRequest;
import ddalkak.member.repository.refreshtoken.RefreshTokenRepository;
import ddalkak.member.service.MemberService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringSecurityLoginTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberService memberService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    void 로그인_성공() throws Exception {
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
        resultActions
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(header().string(HttpHeaders.SET_COOKIE, Matchers.containsString("refreshToken")))
                .andExpect(jsonPath("$.memberId").value(member.getMemberId()))
                .andExpect(jsonPath("$.name").value(member.getName()))
                .andExpect(jsonPath("$.email").value(member.getEmail()));
        assertThat(refreshTokenRepository.findByMemberId(member.getMemberId())).isPresent();
    }

    @ParameterizedTest
    @MethodSource("provideLoginRequest")
    void 로그인_실패(SignUpRequest signUpRequest, LoginRequest loginRequest) throws Exception {
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
