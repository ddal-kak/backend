package ddalkak.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<MemberType> roles = new ArrayList<>();

    public Member() {
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static Member createGeneralMember(String name, String email, String encodedPassword) {
        Member member = Member.builder()
                .name(name)
                .email(email)
                .password(encodedPassword)
                .build();
        member.addRole(MemberType.USER);
        return member;
    }

    private void addRole(MemberType memberType) {
        roles.add(memberType);
    }
}
