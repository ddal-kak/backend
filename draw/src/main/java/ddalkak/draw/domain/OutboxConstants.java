package ddalkak.draw.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OutboxConstants {
    POLLING_BATCHSIZE(100);

    private final int constant;
}
