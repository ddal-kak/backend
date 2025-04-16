package ddalkak.member.service;

import ddalkak.member.domain.Member;
import ddalkak.member.dto.request.SignUpRequest;
import ddalkak.member.common.exception.DuplicatedEmailException;
import ddalkak.member.common.exception.errorcode.ErrorCode;
import ddalkak.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member signup(final SignUpRequest request) {
        validateDuplicatedEmail(request.email());
        return memberRepository.save(Member.createGeneralMember(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password())
        ));
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
