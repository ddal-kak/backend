package ddalkak.prize.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Prize {
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
    private Long probabilityRange;

    @Column(nullable = false)
    private Long randomNumber;

    @Column @CreatedDate
    private LocalDateTime createdAt;

    @Column @LastModifiedDate
    private LocalDateTime updatedAt;

    public Prize( String name, Integer quantity, Integer price, Long probabilityRange, Long randomNumber) {

        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.probabilityRange = probabilityRange;
        this.randomNumber = randomNumber;

    }

    public Prize() {

    }
}
