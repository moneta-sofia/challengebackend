package elxrojo.transaction_service.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import elxrojo.transaction_service.model.ActivityType;
import elxrojo.transaction_service.model.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {

    private Long id;
    private Float amount;
    private ActivityType activityType;
    private TransactionType transactionType;
    private String origin;
    private String name;
    private String destination;
    private Date dated;
    private Long accountId;

}