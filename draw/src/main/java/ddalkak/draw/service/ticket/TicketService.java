package ddalkak.draw.service.ticket;

import ddalkak.draw.domain.entity.Ticket;
import ddalkak.draw.dto.response.TicketResponse;
import ddalkak.draw.repository.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    @Transactional
    public void initTicket(final long newMemberId) {
        ticketRepository.save(Ticket.of(newMemberId));
    }

    @Transactional
    public void useTicket(final long memberId) {
        Ticket ticket = ticketRepository.findByMemberId(memberId)
                .orElseThrow();
        ticket.decrease();
    }

    @Transactional
    public void rewardDailyLogin(final long memberId, final Instant loginAt) {
        Ticket ticket = ticketRepository.findByMemberId(memberId)
                .orElseThrow();
        ticket.rewardDailyLogin(toLocalDate(loginAt));
    }

    @Transactional(readOnly = true)
    public TicketResponse findTicket(final long memberId) {
        return TicketResponse.of(
                ticketRepository.findByMemberId(memberId)
                        .orElseThrow()
        );
    }

    private LocalDate toLocalDate(Instant instant) {
        return instant.atZone(ZoneId.of("Asia/Seoul")).toLocalDate();
    }
}