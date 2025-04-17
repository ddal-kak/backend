package ddalkak.member.repository;

import ddalkak.member.domain.Member;
import ddalkak.member.dto.request.SignUpRequest;
import ddalkak.member.repository.member.DataJpaMemberRepository;
import ddalkak.member.repository.member.JpaMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaMemberRepositoryTest {
    @Autowired
    private DataJpaMemberRepository dataJpaMemberRepository;
    private JpaMemberRepository jpaMemberRepository;

    @BeforeEach
    void init() {
        jpaMemberRepository = new JpaMemberRepository(dataJpaMemberRepository);
    }

    @Test
    void 아이디는_중복될_수_없다() {
        //given
        Member member1 = createMember(new SignUpRequest("홍길동", "exam@example.com", "password"));
        Member member2 = createMember(new SignUpRequest("홍길동2", "exam@example.com", "password2"));

        //when
        jpaMemberRepository.save(member1);

        //then
        Assertions.assertThatThrownBy(() -> jpaMemberRepository.save(member2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void 회원을_저장할_수_있다() {
        //given
        Member member = createMember(new SignUpRequest("홍길동", "exam@example.com", "password"));

        //when
        Member savedMember = jpaMemberRepository.save(member);

        //then
        Assertions.assertThat(memberIsSame(member, savedMember)).isTrue();
    }

    private Member createMember(SignUpRequest signUpRequest) {
        return Member.createGeneralMember(
                signUpRequest.name(),
                signUpRequest.email(),
                signUpRequest.password()
        );
    }

    private boolean memberIsSame(Member mem1, Member mem2) {
        return mem1.getEmail() == mem2.getEmail()
                && mem1.getName() == mem2.getName()
                && mem1.getPassword() == mem2.getPassword();
    }
}