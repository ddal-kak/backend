package ddalkak.draw.domain;

import ddalkak.draw.dto.event.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EventType {
    DRAW_WIN(DrawWinEvent.class, "draw.win"),
    SIGNUP(SignUpEvent.class, "member.signup"),
    LOGIN(LoginEvent.class, "member.login"),
    DECREASE_STOCK(DecreaseStockEvent.class, "prize.decrease-result");

    private final Class<? extends ExternalEvent> targetClass;
    private final String topic;
}
