package elxrojo.transaction_service.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import elxrojo.transaction_service.model.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {

    private Long id;
    private Float amount;
    private TransactionType transactionType;
    private String origin;
    private String name;
    private String destination;
    private Date dated;
    private Long accountId;

}
