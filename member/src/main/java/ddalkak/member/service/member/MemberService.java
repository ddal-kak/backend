package ddalkak.member.service.member;

import ddalkak.member.domain.entity.Member;
import ddalkak.member.dto.event.InternalSignUpEvent;
import ddalkak.member.dto.request.SignUpRequest;
import ddalkak.member.common.exception.DuplicatedEmailException;
import ddalkak.member.common.exception.errorcode.ErrorCode;
import ddalkak.member.repository.member.MemberRepository;
import ddalkak.member.service.event.UniqueIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final UniqueIdGenerator idGenerator;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     *
     * @return savedMember
     * 회원가입 외 부가적인 로직은 애플리케이션 이벤트를 발행해 별도로 처리
     */
    @Transactional
    public Member signup(final SignUpRequest request) {
        validateDuplicatedEmail(request.email());
        Member savedMember = memberRepository.save(Member.createGeneralMember(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password())
        ));
        applicationEventPublisher.publishEvent(InternalSignUpEvent.of(idGenerator.generate(), savedMember.getMemberId()));
        return savedMember;
    }

    @Transactional(readOnly = true)
    public Member findMember(final Long id) {
        return memberRepository.findById(id)
                .orElseThrow();
    }

    private void validateDuplicatedEmail(final String email) {
        if (isDuplicatedEmail(email)) {
            throw new DuplicatedEmailException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    private boolean isDuplicatedEmail(final String email) {
        return memberRepository.findByEmail(email)
                .isPresent();
    }
}
