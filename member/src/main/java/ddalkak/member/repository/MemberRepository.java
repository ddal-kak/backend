package ddalkak.member.repository;

import ddalkak.member.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByEmail(String email);
}
