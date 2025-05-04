package ddalkak.prize.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Prize extends BaseEntity{
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



    public Prize( String name, Integer quantity, Integer price, Long probabilityRange, Long randomNumber) {

        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.probabilityRange = probabilityRange;
        this.randomNumber = randomNumber;

    }

    public Prize() {

    }

    public void update(  String name, Integer quantity, Integer price ) {
        if(name!= null) this.name = name;
        if(quantity != null) this.quantity = quantity;
        if(price != null) this.price = price;
    }
}
