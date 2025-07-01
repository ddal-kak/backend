package ddalkak.draw.domain.entity;

import ddalkak.draw.common.exception.InsufficientTicketException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long memberId;
    private int quantity;
    // 더블 클릭 등으로 인한 응모권 사용 동시성 제어
    // 로그인 중복 메세지 발행으로 인한 동시성 이슈를 제어
    @Version
    private Long version;
    private LocalDate lastLogin;

    public static Ticket of(long memberId) {
        return Ticket.builder()
                .memberId(memberId)
                .quantity(0)
                .build();
    }

    public void decrease() {
        if (quantity <= 0) {
            throw new InsufficientTicketException();
        }
        quantity--;
    }

    public void rewardDailyLogin(LocalDate today) {
        if (lastLogin == null || !lastLogin.isEqual(today)) {
            quantity++;
            lastLogin = today;
        }
    }

    @Builder
    public Ticket(LocalDate lastLogin, int quantity, Long memberId) {
        this.lastLogin = lastLogin;
        this.quantity = quantity;
        this.memberId = memberId;
    }

    public Ticket() {
    }
}
