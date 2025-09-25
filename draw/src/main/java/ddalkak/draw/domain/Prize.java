package ddalkak.draw.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Prize(@JsonProperty("name") String name,
                    @JsonProperty("probabilityRange") Long range,
                    @JsonProperty("randomNumber") Long winNumber) {
}
