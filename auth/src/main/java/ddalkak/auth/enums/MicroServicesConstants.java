package ddalkak.auth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MicroServicesConstants {
    MEMBER_SERVICE("/api/members"),
    DRAW_SERVICE("/api/draws"),
    TICKET_SERVICE("/api/tickets"),
    PRIZE_SERVICE("/api/prizes");

    private final String prefix;
}
