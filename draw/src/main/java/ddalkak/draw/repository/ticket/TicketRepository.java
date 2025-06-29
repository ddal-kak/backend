package ddalkak.draw.repository.ticket;

import ddalkak.draw.domain.entity.Ticket;

import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> findByMemberId(long memberId);
}
