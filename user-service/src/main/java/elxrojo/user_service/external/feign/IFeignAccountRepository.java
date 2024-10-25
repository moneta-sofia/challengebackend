package elxrojo.user_service.external.feign;

import elxrojo.user_service.model.DTO.AccountDTO;
import elxrojo.user_service.model.DTO.CardDTO;
import elxrojo.user_service.model.DTO.TransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "account-service", url = "http://localhost:8085")
public interface IFeignAccountRepository {

    @PostMapping("/accounts/create")
    Long createAccount(@RequestBody AccountDTO accountDTO);

    @GetMapping("/accounts/{userId}")
    AccountDTO getAccountByUser(@PathVariable Long userId);

    @PutMapping("/accounts/{accountId}")
    AccountDTO updateAccount(@PathVariable Long accountId, @RequestBody AccountDTO account);

    @GetMapping("/accounts/{userId}/transactions")
    List<TransactionDTO> getTransactionsByAccount(@PathVariable Long userId, @RequestParam(required = false) Integer limit);

    @PostMapping("/accounts/{accountId}/card")
    void createAccountCard(@RequestBody CardDTO card, @RequestParam Long accountId);

    @GetMapping("accounts/{accountId}/card")
    List<CardDTO> getCardsByAccount(@PathVariable Long accountId);

}
