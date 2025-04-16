package ddalkak.member.dto.request;

import jakarta.validation.constraints.*;

public record SignUpRequest(@NotBlank String name,
                            @NotBlank @Email String email,
                            @NotBlank @Size(min = 8, max = 20) String password) {
}
