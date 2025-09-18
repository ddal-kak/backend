package ddalkak.prize.service.prize.impl;

import ddalkak.prize.config.error.exception.OutOfStockException;
import ddalkak.prize.config.error.exception.PageOutOfBoundsException;
import ddalkak.prize.config.error.exception.PrizeNotFoundException;
import ddalkak.prize.domain.entity.Prize;
import ddalkak.prize.dto.request.PrizeSaveRequestDto;
import ddalkak.prize.dto.request.PrizeUpdateRequestDto;
import ddalkak.prize.dto.response.AdminPrizeResponseDto;
import ddalkak.prize.dto.response.PageResponseDto;
import ddalkak.prize.dto.response.PrizeResponseDto;
import ddalkak.prize.repository.prize.PrizeRepository;
import ddalkak.prize.service.prize.PrizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class PrizeServiceImpl implements PrizeService {

    private final PrizeRepository prizeRepository;

    /**
     * 새로운 상품을 저장합니다.
     *
     * @param prizeSaveRequestDto 상품 저장 요청 DTO
     * @return 저장된 상품의 ID
     */
    @Override
    @Transactional
    public Long save(PrizeSaveRequestDto prizeSaveRequestDto) {
        log.info("Saving new prize: {}", prizeSaveRequestDto.name());
        // 엔티티 저장
        Prize savedPrize = prizeRepository.save(Prize.from(prizeSaveRequestDto));
        // 저장된 엔티티의 ID 반환
        return savedPrize.getId();
    }


    /**
     * 페이지네이션된 상품 목록을 조회합니다.
     *
     * @param size 페이지 크기
     * @return 상품 응답 DTO 페이지
     * @throws PageOutOfBoundsException 페이지 번호가 범위를 벗어난 경우
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto getPrizePage(int size, Long lastId) {
        Pageable pageable = Pageable.ofSize(size + 1);
        List<PrizeResponseDto> resultPage = getResultPage(lastId, pageable);
        boolean hasNext = resultPage.size() == size + 1;
        return PageResponseDto.of(
                resultPage.stream()
                        .limit(size)
                        .collect(Collectors.toList()),
                hasNext);
    }

    /**
     * ID로 상품을 조회합니다.
     *
     * @param id 상품 ID
     * @return 상품 응답 DTO
     * @throws PrizeNotFoundException 상품을 찾을 수 없는 경우
     */

    @Override
    public AdminPrizeResponseDto getPrize(Long id) {
        log.info("Fetching prize with id: {}", id);
        return prizeRepository.findById(id)
                .map(prize -> AdminPrizeResponseDto.of(prize))
                .orElseThrow(PrizeNotFoundException::new);
    }

    /**
     * 상품 정보를 업데이트합니다.
     *
     * @param prizeUpdateRequestDto 상품 업데이트 요청 DTO
     * @return 업데이트된 상품의 ID
     * @throws PrizeNotFoundException 상품을 찾을 수 없는 경우
     */
    @Override
    @Transactional
    public Long updatePrize(PrizeUpdateRequestDto prizeUpdateRequestDto) {
        log.info("Updating prize with id: {}", prizeUpdateRequestDto.id());
        Prize prize = prizeRepository.findById(prizeUpdateRequestDto.id())
                .orElseThrow(PrizeNotFoundException::new);
        prize.update(
                prizeUpdateRequestDto.name(),
                prizeUpdateRequestDto.quantity(),
                prizeUpdateRequestDto.price()
        );
        return prize.getId();
    }

    /**
     * 상품의 재고를 감소시킵니다.
     *
     * @param prizeId 상품 ID
     * @throws PrizeNotFoundException 상품을 찾을 수 없는 경우
     */
    // 재고 감소 메서드
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decreaseStock(Long prizeId) {
        Prize prize = prizeRepository.findById(prizeId)
                .orElseThrow(PrizeNotFoundException::new);
        if (prize.getQuantity() <= 0) {
            throw new OutOfStockException();
        } else {
            prize.update(null, prize.getQuantity() - 1, null);
        }

    }

    private List<PrizeResponseDto> getResultPage(Long lastId, Pageable pageable) {
        if (lastId == null) {
            return prizeRepository.findAllByIdDesc(pageable)
                    .map(prize -> PrizeResponseDto.of(prize))
                    .getContent();
        }
        return prizeRepository.findAllByIdDesc(lastId, pageable)
                .map(prize -> PrizeResponseDto.of(prize))
                .getContent();
    }

}