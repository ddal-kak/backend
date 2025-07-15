package ddalkak.auth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MicroServicesConstants {
    MEMBER_SERVICE("/member"),
    DRAW_SERVICE("/draw"),
    TICKET_SERVICE("/ticket"),
    PRIZE_SERVICE("/prize");

    private final String prefix;
}
