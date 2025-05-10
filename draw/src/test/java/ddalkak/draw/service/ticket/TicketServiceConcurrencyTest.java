package ddalkak.draw.service.ticket;

import ddalkak.draw.domain.entity.Ticket;
import ddalkak.draw.repository.ticket.JpaTicketRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootTest
class TicketServiceConcurrencyTest {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private JpaTicketRepository ticketRepository;

    @Test
    void 동시_응모권_차감_시_낙관적락_예외가_발생() throws InterruptedException {
        //given
        Ticket ticket = new Ticket(1L, 2);
        ticketRepository.save(ticket);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);
        AtomicBoolean occurEx = new AtomicBoolean(false);

        //when
        for (int i = 0; i < 2; i++) {
            executorService.execute(() -> {
                try {
                    ticketService.useTicket(1L);
                } catch (OptimisticLockingFailureException e) {
                    occurEx.set(true);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        //then
        Assertions.assertThat(occurEx.get()).isTrue();
    }
}
