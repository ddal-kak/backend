package ddal_kak.prize.controller;

import ddal_kak.prize.domain.dto.PrizeDto;
import ddal_kak.prize.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prize")
public class PrizeController {
    private final PrizeService prizeService;

    @Autowired
    public PrizeController(PrizeService prizeService){
        this.prizeService = prizeService;
    }

    @PostMapping("/save")
    public ResponseEntity<PrizeDto> setPrize(@RequestBody PrizeDto prizeRequestDto) {

        return ResponseEntity.ok(prizeService.save(prizeRequestDto));

    }

}
