package ddalkak.auth.config;

import ddalkak.auth.validator.AdminValidator;
import ddalkak.auth.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SupportedValidatorConfig {
    private final AdminValidator adminValidator;

    @Bean
    public List<Validator> validators() {
        List<Validator> validators = new ArrayList<>();
        validators.add(adminValidator);
        return validators;
    }
}
