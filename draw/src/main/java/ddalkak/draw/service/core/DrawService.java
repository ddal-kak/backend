package ddalkak.draw.service.core;

import ddalkak.draw.domain.DrawResult;
import ddalkak.draw.domain.entity.Draw;
import ddalkak.draw.dto.event.DrawWinEvent;
import ddalkak.draw.dto.response.DrawResultResponse;
import ddalkak.draw.repository.draw.DrawRepository;
import ddalkak.draw.service.event.UniqueIdGenerator;
import ddalkak.draw.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrawService {
    private final TicketService ticketService;
    private final DrawMachine drawMachine;
    private final DrawRepository drawRepository;
    private final UniqueIdGenerator idGenerator;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void luckyDraw(final long memberId, final long prizeId) {
        // 유저 응모권을 한장 소모한다.
        ticketService.useTicket(memberId);
        // 경품 응모 머신으로부터 결과를 받아온 뒤 저장한다.
        Draw draw = drawMachine.attempt(memberId, prizeId);
        drawRepository.save(draw);

        if (draw.isWinPending()) {
            applicationEventPublisher.publishEvent(DrawWinEvent.of(idGenerator.generate(), draw.getId(), prizeId));
        }
    }

    @Transactional
    public void finalizeDrawResult(final long drawId, final DrawResult result) {
        Draw draw = drawRepository.findById(drawId)
                .orElseThrow();
        draw.setResult(result);
    }

    @Transactional(readOnly = true)
    public List<DrawResultResponse> findDrawResult(final long memberId) {
        return drawRepository.findAllByMemberId(memberId)
                .stream()
                .map(draw -> DrawResultResponse.of(draw))
                .collect(Collectors.toList());
    }
}
