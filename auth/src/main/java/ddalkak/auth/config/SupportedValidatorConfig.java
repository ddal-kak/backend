package ddalkak.auth.config;

import ddalkak.auth.validator.AdminValidator;
import ddalkak.auth.validator.OwnerCheckValidator;
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
    private final OwnerCheckValidator ownerCheckValidator;

    @Bean
    public List<Validator> validators() {
        List<Validator> validators = new ArrayList<>();
        validators.add(adminValidator);
        validators.add(ownerCheckValidator);
        return validators;
    }
}
