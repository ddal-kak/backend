package ddalkak.prize.repository;

import ddalkak.prize.domain.dto.PrizeRequestDto;
import ddalkak.prize.domain.entity.Prize;

public interface PrizeRepository {
    Prize save(Prize prize);


}
