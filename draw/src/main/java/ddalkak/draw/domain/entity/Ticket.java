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
    private Long memberId;
    private int quantity;
    @Version
    private Long version;// 더블 클릭 등으로 인한 응모권 사용 동시성 제어
    private LocalDate lastLogin;

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
