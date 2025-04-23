package ddalkak.member.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
public class RefreshToken extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Column(length = 1000)
    private String token;

    public RefreshToken() {

    }

    @Builder
    public RefreshToken(Member member, String token) {
        this.member = member;
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
