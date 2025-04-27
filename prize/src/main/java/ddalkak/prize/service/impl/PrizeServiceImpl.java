package ddalkak.prize.service.impl;

import ddalkak.prize.config.error.exception.PageOutOfBoundsException;
import ddalkak.prize.config.error.exception.PrizeNotFoundException;
import ddalkak.prize.domain.dto.PrizeResponseDto;
import ddalkak.prize.domain.dto.PrizeSaveRequestDto;
import ddalkak.prize.domain.dto.PrizeUpdateRequestDto;
import ddalkak.prize.domain.entity.Prize;
import ddalkak.prize.repository.PrizeRepository;
import ddalkak.prize.service.PrizeService;
import ddalkak.prize.service.util.RandomNumberGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



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
        // 난수 생성
        Long randomNumber = RandomNumberGenerator.ofRange(prizeSaveRequestDto.probabilityRange());

        Prize prize = new Prize(
                prizeSaveRequestDto.name(),
                prizeSaveRequestDto.quantity(),
                prizeSaveRequestDto.price(),
                prizeSaveRequestDto.probabilityRange(),
                randomNumber
        );

        // 엔티티 저장
        Prize savedPrize = prizeRepository.save(prize);

        // 저장된 엔티티의 ID 반환
        return savedPrize.getId();
    }


    /**
     * 페이지네이션된 상품 목록을 조회합니다.
     *
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 상품 응답 DTO 페이지
     * @throws PageOutOfBoundsException 페이지 번호가 범위를 벗어난 경우
     */

    @Override
    public Page<PrizeResponseDto> getPrizePage(int page, int size) {
        log.info("Fetching prize page: {}, size: {}", page, size);
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<PrizeResponseDto> resultPage = prizeRepository.findAllByIdDesc(pageable)
                .map(PrizeResponseDto::new);
        if(page >= resultPage.getTotalPages() && resultPage.getTotalPages() >= 0) {
           throw new PageOutOfBoundsException();
        }
        return resultPage;

    }
    /**
     * ID로 상품을 조회합니다.
     *
     * @param id 상품 ID
     * @return 상품 응답 DTO
     * @throws PrizeNotFoundException 상품을 찾을 수 없는 경우
     */

    @Override
    public PrizeResponseDto getPrize(Long id) {
        log.info("Fetching prize with id: {}", id);
        return prizeRepository.findById(id)
                .map(PrizeResponseDto::new)
                .orElseThrow(() -> new PrizeNotFoundException());
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
               .orElseThrow(() -> new PrizeNotFoundException());
       prize.update(
               prizeUpdateRequestDto.name(),
               prizeUpdateRequestDto.quantity(),
               prizeUpdateRequestDto.price()
       );
       return prize.getId();
    }



}
