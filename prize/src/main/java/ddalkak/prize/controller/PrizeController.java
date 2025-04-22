package ddalkak.prize.controller;

import ddalkak.prize.domain.dto.PrizeRequestDto;
import ddalkak.prize.domain.dto.PrizeResponseDto;
import ddalkak.prize.service.PrizeService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prize")
@RequiredArgsConstructor
@Validated
public class PrizeController {
    @Autowired
    private final PrizeService prizeService;

    @PostMapping
    public ResponseEntity<Long> setPrize(@Valid @RequestBody PrizeRequestDto prizeRequestDto) {

        return ResponseEntity.ok(prizeService.save(prizeRequestDto));

    }
    @GetMapping
    public ResponseEntity< List<PrizeResponseDto> > getPrizePage (
        @PositiveOrZero(message = "페이지는 0이상이어야~~") @RequestParam(defaultValue = "0") int page,
        @Positive(message = "사이즈는1이상이어야 ~") @RequestParam(defaultValue = "5") int size )
    {
        return ResponseEntity.ok(prizeService.getPrizePage(page, size).getContent());
    }



    @GetMapping("/{id}")
    public ResponseEntity<PrizeResponseDto> getPrize(@PathVariable Long id) {
        return ResponseEntity.ok(prizeService.getPrize(id));
    }

}
