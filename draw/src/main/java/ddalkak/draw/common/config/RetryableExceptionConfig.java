package ddalkak.draw.common.config;

import io.netty.channel.ConnectTimeoutException;
import io.netty.handler.timeout.ReadTimeoutException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.dao.TransientDataAccessException;

import java.net.SocketTimeoutException;
import java.util.List;

@Configuration
public class RetryableExceptionConfig {
    @Bean
    public List<Class<? extends Throwable>> retryableExceptions() {
        return List.of(
                SocketTimeoutException.class,
                ConnectTimeoutException.class,
                ReadTimeoutException.class,
                TransientDataAccessException.class,
                RecoverableDataAccessException.class
        );
    }
}
