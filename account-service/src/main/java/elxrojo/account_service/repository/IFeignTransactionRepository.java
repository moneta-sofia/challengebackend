package elxrojo.account_service.repository;

import elxrojo.account_service.model.DTO.TransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name= "transaction-service", url = "http://localhost:8086")
public interface IFeignTransactionRepository {

    @GetMapping("/transaction/{accountId}")
    TransactionDTO getTransactionByAccountId(@PathVariable Long accountId);
}
