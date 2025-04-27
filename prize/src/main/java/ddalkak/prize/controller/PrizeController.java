package ddalkak.prize.controller;

import ddalkak.prize.domain.dto.PrizeSaveRequestDto;
import ddalkak.prize.domain.dto.PrizeResponseDto;
import ddalkak.prize.domain.dto.PrizeUpdateRequestDto;
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
    public ResponseEntity<Long> setPrize(@Valid @RequestBody PrizeSaveRequestDto prizeSaveRequestDto) {

        return ResponseEntity.ok(prizeService.save(prizeSaveRequestDto));

    }
    @GetMapping
    public ResponseEntity< List<PrizeResponseDto> > getPrizePage (
        @PositiveOrZero(message = "처음 페이지입니다.") @RequestParam(defaultValue = "0") int page,
        @Positive(message = "요청 데이터수는 1개 이상이어야 합니다.") @RequestParam(defaultValue = "5") int size )
    {
        return ResponseEntity.ok(prizeService.getPrizePage(page, size).getContent());
    }



    @GetMapping("/{id}")
    public ResponseEntity<PrizeResponseDto> getPrize(@PathVariable Long id) {
        return ResponseEntity.ok(prizeService.getPrize(id));
    }
    @PatchMapping
    public ResponseEntity<Long> updatePrize(@Valid @RequestBody PrizeUpdateRequestDto prizeUpdateRequestDto) {
        return ResponseEntity.ok(prizeService.updatePrize(prizeUpdateRequestDto));
    }

}
