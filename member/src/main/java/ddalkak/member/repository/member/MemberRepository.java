package ddalkak.member.repository.member;

import ddalkak.member.domain.entity.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByEmail(String email);
}
