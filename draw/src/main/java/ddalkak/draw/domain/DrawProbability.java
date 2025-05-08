package ddalkak.draw.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DrawProbability(@JsonProperty("probabilityRange") Long range,
                              @JsonProperty("randomNumber") Long winNumber) {
}
