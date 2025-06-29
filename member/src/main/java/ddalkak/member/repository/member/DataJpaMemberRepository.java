package ddalkak.member.repository.member;

import ddalkak.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataJpaMemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
