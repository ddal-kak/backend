package ddalkak.draw.controller;

import ddalkak.draw.service.core.DrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/draw")
@RequiredArgsConstructor
public class DrawController {
    private final DrawService drawService;

    @GetMapping("/{prizeId}")
    public ResponseEntity<Void> draw(@RequestHeader("X-Member-Id") long memberId,
                                     @PathVariable long prizeId) {
        drawService.luckyDraw(memberId, prizeId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
