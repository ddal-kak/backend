package ddalkak.member.service;

import ddalkak.member.domain.Member;
import ddalkak.member.dto.request.SignUpRequest;
import ddalkak.member.common.exception.DuplicatedEmailException;
import ddalkak.member.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private MemberService memberService;

    @Test
    void 회원가입_성공() {
        //given
        SignUpRequest request = new SignUpRequest("홍길동", "test@email.com", "password");
        Member expected = Member.createGeneralMember(request.name(), request.email(), request.password());
        when(memberRepository.findByEmail(request.email()))
                .thenReturn(Optional.empty());
        when(memberRepository.save(any()))
                .thenReturn(expected);
        when(passwordEncoder.encode(request.password()))
                .thenReturn(request.password());

        //when
        Member savedMember = memberService.signup(request);

        //then
        Assertions.assertThat(memberIsSame(expected, savedMember)).isTrue();
    }

    @Test
    void 회원가입_실패_중복된_이메일() {
        //given
        SignUpRequest request = new SignUpRequest("홍길동", "test@email.com", "password");
        when(memberRepository.findByEmail(request.email()))
                .thenReturn(Optional.of(new Member()));

        //when & then
        Assertions.assertThatThrownBy(() -> memberService.signup(request))
                .isInstanceOf(DuplicatedEmailException.class);
    }

    private boolean memberIsSame(Member mem1, Member mem2) {
        return mem1.getName() == mem2.getName() &&
                mem1.getEmail() == mem2.getEmail() &&
                mem1.getPassword() == mem2.getPassword();
    }
}