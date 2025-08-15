package ddalkak.draw.controller;

import ddalkak.draw.dto.response.DrawResultResponse;
import ddalkak.draw.service.core.DrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/draws")
@RequiredArgsConstructor
public class DrawController {
    private final DrawService drawService;

    @GetMapping("/prizes/{prizeId}")
    public ResponseEntity<Void> luckyDraw(@RequestHeader("X-Member-Id") long memberId,
                                     @PathVariable long prizeId) {
        drawService.luckyDraw(memberId, prizeId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<List<DrawResultResponse>> findDrawResult(@PathVariable long memberId) {
        return ResponseEntity.ok(drawService.findDrawResult(memberId));
    }
}
