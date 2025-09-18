package ddalkak.prize.config;

import jakarta.validation.ConstraintViolationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.dao.TransientDataAccessException;

import java.net.SocketTimeoutException;
import java.util.List;

@Configuration
public class RetryableExceptionConfig {

    @Bean
    public List<Class<? extends Throwable>> retryableExceptions(){
        return List.of(
                SocketTimeoutException.class,
                TransientDataAccessException.class,
                RecoverableDataAccessException.class
        );
    }
    @Bean
    public List<Class<? extends Throwable>> nonRetryableExceptions(){
        return List.of(
                ConstraintViolationException.class,
                DataIntegrityViolationException.class
        );
    }

}
