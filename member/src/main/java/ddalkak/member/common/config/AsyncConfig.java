package ddalkak.member.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 2vCPU 기준, 작업의 대부분은 I/O Bound 이기에 코어 수의 2배 할당
        executor.setCorePoolSize(4);
        // 버스트용, 두배 할당
        executor.setMaxPoolSize(8);
        // 작업 대기 큐, 이 큐가 꽉차면 버스트용 스레드 생성
        executor.setQueueCapacity(20);
        // 버스트용 스레드 유지 시간
        executor.setKeepAliveSeconds(30);
        executor.setThreadNamePrefix("async-executor");
        executor.initialize();
        return executor;
    }
}
