package ddalkak.draw.repository.ticket;

import ddalkak.draw.domain.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTicketRepository extends JpaRepository<Ticket, Long>, TicketRepository {
}
