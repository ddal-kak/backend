package ddalkak.draw.service.ticket;

import ddalkak.draw.common.exception.InsufficientTicketException;
import ddalkak.draw.domain.entity.Ticket;
import ddalkak.draw.repository.ticket.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @InjectMocks
    private TicketService ticketService;
    @Mock
    private TicketRepository ticketRepository;

    @Test
    void 정상_응모권_감소() {
        //given
        Ticket ticket = Ticket.builder()
                .quantity(1)
                .memberId(1L)
                .build();
        Mockito.when(ticketRepository.findByMemberId(1L))
                .thenReturn(Optional.of(ticket));

        //when
        ticketService.useTicket(1L);

        //then
        assertThat(ticket.getQuantity()).isEqualTo(0);
    }

    @Test
    void 예외발생_응모권_부족() {
        //given
        Ticket ticket = Ticket.builder()
                .quantity(0)
                .memberId(1L)
                .build();
        Mockito.when(ticketRepository.findByMemberId(1L))
                .thenReturn(Optional.of(ticket));

        //when & then
        assertThatThrownBy(() -> ticketService.useTicket(1L))
                .isInstanceOf(InsufficientTicketException.class);
    }
}