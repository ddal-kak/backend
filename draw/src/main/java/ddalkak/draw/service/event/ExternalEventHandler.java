package ddalkak.draw.service.event;

import ddalkak.draw.common.aop.annotation.EnableIdempotent;
import ddalkak.draw.domain.DrawResult;
import ddalkak.draw.dto.event.DecreaseResult;
import ddalkak.draw.dto.event.DecreaseStockEvent;
import ddalkak.draw.dto.event.LoginEvent;
import ddalkak.draw.dto.event.SignUpEvent;
import ddalkak.draw.service.core.DrawService;
import ddalkak.draw.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ExternalEventHandler {
    private final TicketService ticketService;
    private final DrawService drawService;

    @Transactional
    @EnableIdempotent(eventId = "#event.eventId()")
    public void handleLoginEvent(LoginEvent event) {
        ticketService.rewardDailyLogin(event.memberId(), event.occurAt());
    }

    @Transactional
    @EnableIdempotent(eventId = "#event.eventId()")
    public void handleSignupEvent(SignUpEvent event) {
        ticketService.initTicket(event.memberId());
    }

    @Transactional
    @EnableIdempotent(eventId = "#event.eventId()")
    public void handleDecreaseStockEvent(final DecreaseStockEvent event) {
        if (event.result() == DecreaseResult.SUCCESS) {
            drawService.finalizeDrawResult(event.drawId(), DrawResult.WIN);
        } else if (event.result() == DecreaseResult.FAILURE) {
            drawService.finalizeDrawResult(event.drawId(), DrawResult.LOSE);
        } else {
            drawService.finalizeDrawResult(event.drawId(), DrawResult.ERROR);
            ticketService.increaseTicket(event.memberId());
        }
    }
}
