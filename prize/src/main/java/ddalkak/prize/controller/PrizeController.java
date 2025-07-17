package ddalkak.prize.controller;

import ddalkak.prize.dto.request.PrizeSaveRequestDto;
import ddalkak.prize.dto.response.AdminPrizeResponseDto;
import ddalkak.prize.dto.request.PrizeUpdateRequestDto;
import ddalkak.prize.dto.response.PageResponseDto;
import ddalkak.prize.dto.response.PrizeResponseDto;
import ddalkak.prize.service.prize.PrizeService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prizes")
@RequiredArgsConstructor
@Validated
public class PrizeController {

    private final PrizeService prizeService;

    @PostMapping
    public ResponseEntity<Long> setPrize(@Valid @RequestBody PrizeSaveRequestDto prizeSaveRequestDto) {

        return ResponseEntity.ok(prizeService.save(prizeSaveRequestDto));

    }
    @GetMapping
    public ResponseEntity<PageResponseDto> getPrizePage (
        @Positive(message = "요청 데이터수는 1개 이상이어야 합니다.") @RequestParam(defaultValue = "5") int size,
        @RequestParam Long lastId
    )
    {
        return ResponseEntity.ok(prizeService.getPrizePage(size, lastId));
    }



    @GetMapping("/{id}")
    public ResponseEntity<AdminPrizeResponseDto> getPrize(@PathVariable Long id) {
        return ResponseEntity.ok(prizeService.getPrize(id));
    }
    @PatchMapping
    public ResponseEntity<Long> updatePrize(@Valid @RequestBody PrizeUpdateRequestDto prizeUpdateRequestDto) {
        return ResponseEntity.ok(prizeService.updatePrize(prizeUpdateRequestDto));
    }

}
