package ddalkak.member.controller;

import com.google.gson.Gson;
import ddalkak.member.domain.entity.Member;
import ddalkak.member.dto.request.SignUpRequest;
import ddalkak.member.service.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {
    @Mock
    private MemberService memberService;
    @InjectMocks
    private MemberController memberController;
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    void mockMvc는_Null이_아니다() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    void 회원가입_성공() throws Exception {
        //given & when
        SignUpRequest request = new SignUpRequest("홍길동", "exam@exam.com", "password");
        Member savedMember = Member.createGeneralMember("홍길동", "exam@exam.com", "password");
        Mockito.when(memberService.signup(request)).thenReturn(savedMember);
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))
        );
        //then
        resultActions.andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("provideInputs")
    void 유효성_검증_실패(SignUpRequest request) throws Exception {
        //given & when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))
        );
        MvcResult mvcResult = resultActions.andReturn();

        //then
        Assertions.assertThat(mvcResult.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class);
    }

    static Stream<Arguments> provideInputs() {
        return Stream.of(
                Arguments.of(new SignUpRequest("", "exam@exam.com", "passwords")),
                Arguments.of(new SignUpRequest(" ", "exam@exam.com", "passwords")),
                Arguments.of(new SignUpRequest(null, "exam@exam.com", "passwords")),
                Arguments.of(new SignUpRequest("홍길동", "", "passwords")),
                Arguments.of(new SignUpRequest("홍길동", " ", "passwords")),
                Arguments.of(new SignUpRequest("홍길동", "null", "passwords")),
                Arguments.of(new SignUpRequest("홍길동", "exam.com", "passwords")),
                Arguments.of(new SignUpRequest("홍길동", "exam@exam.com", "p")),
                Arguments.of(new SignUpRequest("홍길동", "exam@exam.com", "aaaaaaaaaaaaaaaaaaaaaa"))
        );
    }
}