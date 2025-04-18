package ddalkak.member.repository.member;

import ddalkak.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {
    private final DataJpaMemberRepository dataJpaMemberRepository;

    @Override
    public Member save(Member member) {
        return dataJpaMemberRepository.save(member);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return dataJpaMemberRepository.findByEmail(email);
    }
}
