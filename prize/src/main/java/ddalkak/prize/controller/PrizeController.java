package ddalkak.prize.controller;

import ddalkak.prize.config.error.exception.InvalidPageRequestException;
import ddalkak.prize.domain.dto.PrizeRequestDto;
import ddalkak.prize.domain.dto.PrizeResponseDto;
import ddalkak.prize.service.PrizeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prize")
@RequiredArgsConstructor
public class PrizeController {
    @Autowired
    private final PrizeService prizeService;

    @PostMapping
    public ResponseEntity<Long> setPrize(@Valid @RequestBody PrizeRequestDto prizeRequestDto) {

        return ResponseEntity.ok(prizeService.save(prizeRequestDto));

    }
    @GetMapping
    public ResponseEntity< List<PrizeResponseDto> > getPrizePage (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size ) {
        if (page < 0 || size <= 0) {
            throw new InvalidPageRequestException();
        }
        return ResponseEntity.ok(prizeService.getPrizePage(page, size).getContent());
    }
    @GetMapping("/{id}")
    public ResponseEntity<PrizeResponseDto> getPrize(@PathVariable Long id) {
        return ResponseEntity.ok(prizeService.getPrize(id));
    }

}
