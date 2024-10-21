package elxrojo.user_service.repository;

import elxrojo.user_service.model.DTO.AccountDTO;
import elxrojo.user_service.model.DTO.TransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name= "account-service", url= "http://localhost:8085")
 public interface IFeignAccountRepository {

    @PostMapping("/accounts/create")
    Long createAccount(@RequestBody AccountDTO accountDTO);

    @GetMapping("/accounts/{userId}")
    AccountDTO getAccountByUser(@PathVariable Long userId);

    @GetMapping("accounts/{userId}/transactions")
    TransactionDTO getTransactionsByAccount(@PathVariable Long userId);

}
