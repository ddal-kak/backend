package ddalkak.draw.domain.entity;

import ddalkak.draw.common.exception.InsufficientTicketException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private int quantity;
    @Version
    private Long version; // 더블 클릭 등으로 인한 응모권 사용 동시성 제어

    public void decrease() {
        if (quantity <= 0) {
            throw new InsufficientTicketException();
        }
        quantity--;
    }

    @Builder
    public Ticket(Long memberId, int quantity) {
        this.memberId = memberId;
        this.quantity = quantity;
    }

    public Ticket() {
    }
}
