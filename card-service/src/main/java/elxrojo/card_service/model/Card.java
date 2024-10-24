package elxrojo.card_service.model;

import elxrojo.card_service.model.converter.YearMonthAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Entity
@Table(name = "card")
@Getter
@Setter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "cardId")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number", length = 16)
    private String number;

    @Column(name = "cvc", nullable = false)
    private Integer cvc;

    @Column(name = "expirationDate",nullable = false)
    @Convert(converter = YearMonthAttributeConverter.class)
    private YearMonth expirationDate;

    @Column(name = "accountId", nullable = false)
    private Long accountId;

    public Card() {
    }

    public Card(Long id, String name, String number, Integer cvc, YearMonth expirationDate, Long accountId) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.cvc = cvc;
        this.expirationDate = expirationDate;
        this.accountId = accountId;
    }

    public Card(String name, String number, Integer cvc, YearMonth expirationDate, Long accountId) {
        this.name = name;
        this.number = number;
        this.cvc = cvc;
        this.expirationDate = expirationDate;
        this.accountId = accountId;
    }
}
