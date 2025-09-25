package ddalkak.auth.service.validator;

import ddalkak.auth.dto.HttpRequestSignature;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidationAdapter {
    private final List<Validator> validators;

    public Optional<Validator> selectValidator(HttpRequestSignature httpRequestSignature) {
        return validators.stream()
                .filter(validator -> validator.supports(httpRequestSignature))
                .findFirst();
    }
}
