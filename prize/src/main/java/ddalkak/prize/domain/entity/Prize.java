package ddalkak.prize.domain.entity;

import ddalkak.prize.dto.request.PrizeSaveRequestDto;
import ddalkak.prize.service.util.RandomNumberGenerator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Prize extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private Integer quantity;


    @Column(nullable = false)
    private Integer price;

    @Column
    private String imageUrl;

    @Column
    @Lob
    private String description;

    @Column
    private Long probabilityRange;

    @Column(nullable = false)
    private Long randomNumber;

    @Version
    private Long version;

    public static Prize from(PrizeSaveRequestDto prizeSaveRequestDto) {
        return Prize.builder()
                .name(prizeSaveRequestDto.name())
                .price(prizeSaveRequestDto.price())
                .description(prizeSaveRequestDto.description())
                .imageUrl(prizeSaveRequestDto.imageUrl())
                .quantity(prizeSaveRequestDto.quantity())
                .randomNumber(RandomNumberGenerator.ofRange(prizeSaveRequestDto.probabilityRange()))
                .probabilityRange(prizeSaveRequestDto.probabilityRange())
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Prize(String name, Integer quantity, Integer price, Long probabilityRange, Long randomNumber, String description, String imageUrl) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.probabilityRange = probabilityRange;
        this.randomNumber = randomNumber;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Prize() {

    }

    public void update(String name, Integer quantity, Integer price) {
        if (name != null) this.name = name;
        if (quantity != null) this.quantity = quantity;
        if (price != null) this.price = price;
    }


}
