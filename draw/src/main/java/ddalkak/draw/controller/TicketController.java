package ddalkak.draw.controller;

import ddalkak.draw.dto.response.TicketResponse;
import ddalkak.draw.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/members/{memberId}")
    public ResponseEntity<TicketResponse> findTicket(@PathVariable long memberId) {
        return ResponseEntity.ok(ticketService.findTicket(memberId));
    }
}
