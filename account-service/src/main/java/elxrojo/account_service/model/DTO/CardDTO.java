package elxrojo.account_service.model.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO {


    private Long id;
    private String name;
    private String number;
    private Integer cvc;
    private YearMonth expirationDate;
    private Long accountId;


    public CardDTO() {
    }

    public CardDTO(String name, String number, Integer cvc, YearMonth expirationDate, Long accountId) {
        this.name = name;
        this.number = number;
        this.cvc = cvc;
        this.expirationDate = expirationDate;
        this.accountId = accountId;
    }

    public CardDTO(Long id, String name, String number, Integer cvc, YearMonth expirationDate, Long accountId) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.cvc = cvc;
        this.expirationDate = expirationDate;
        this.accountId = accountId;
    }

}
