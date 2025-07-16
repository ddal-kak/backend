package ddalkak.draw.dto.response;

import ddalkak.draw.domain.entity.Ticket;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TicketResponse(long memberId,
                             int quantity) {
    public static TicketResponse of(Ticket ticket) {
        return TicketResponse.builder()
                .memberId(ticket.getMemberId())
                .quantity(ticket.getQuantity())
                .build();
    }
}
