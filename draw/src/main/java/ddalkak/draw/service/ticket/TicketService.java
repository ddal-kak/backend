package ddalkak.draw.service.ticket;

import ddalkak.draw.domain.entity.Ticket;
import ddalkak.draw.repository.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    @Transactional
    public void useTicket(final long memberId) {
        Ticket ticket = ticketRepository.findByMemberId(memberId)
                .orElseThrow();
        ticket.decrease();
    }
}
