package ddalkak.prize.config.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class OutboxSchedulerConfig {
    @Bean
    public ThreadPoolTaskScheduler pollScheduler() {
        ThreadPoolTaskScheduler ts = new ThreadPoolTaskScheduler();
        ts.setPoolSize(1);
        ts.setThreadNamePrefix("outbox-");
        ts.initialize();
        return ts;
    }
}
