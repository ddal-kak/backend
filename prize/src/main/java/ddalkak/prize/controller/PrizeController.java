package ddalkak.prize.controller;

import ddalkak.prize.domain.dto.PrizeRequestDto;
import ddalkak.prize.domain.dto.PrizeResponseDto;
import ddalkak.prize.service.PrizeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prize")
@RequiredArgsConstructor
public class PrizeController {
    @Autowired
    private final PrizeService prizeService;

    @PostMapping("/")
    public ResponseEntity<Long> setPrize(@Valid @RequestBody PrizeRequestDto prizeRequestDto) {

        return ResponseEntity.ok(prizeService.save(prizeRequestDto));

    }

}
