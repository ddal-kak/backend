package ddal_kak.prize.domain.entity;

import ddal_kak.prize.domain.dto.PrizeDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Prize {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private Integer quantity;


    @Column(nullable = false)
    private Integer price;


    @Column
    private Long probabilityRange;

    @Column(nullable = false)
    private Long randomNumber;

    public Prize() { }

    public Prize(String name, Integer quantity, Integer price, Long probabilityRange, Long randomNumber) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.probabilityRange = probabilityRange;
        this.randomNumber = randomNumber;
    }
    public Prize(PrizeDto prizeRequestDto, Long randomNumber ){
        this.name = prizeRequestDto.getName();
        this.quantity = prizeRequestDto.getQuantity();
        this.price = prizeRequestDto.getPrice();
        this.probabilityRange = prizeRequestDto.getProbabilityRange();
        this.randomNumber = randomNumber;
    }

    public PrizeDto toResponseDto () {
        return PrizeDto.builder()
               .id(this.id)
               .name(this.name)
               .quantity(this.quantity)
               .price(this.price)
                .probabilityRange(this.probabilityRange)
               .randomNumber(this.randomNumber)
               .build();

    }
}
