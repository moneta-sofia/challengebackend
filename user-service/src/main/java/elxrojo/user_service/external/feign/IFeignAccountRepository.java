package elxrojo.user_service.external.feign;

import elxrojo.user_service.model.DTO.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "account-service", url = "http://localhost:8085")
public interface IFeignAccountRepository {

    @PostMapping("/accounts/")
    Long createAccount(@RequestBody AccountDTO accountDTO);

//    @GetMapping("/accounts/{userId}")
//    AccountDTO getAccountByUser(@PathVariable Long userId);
//
    @PutMapping("/accounts/{accountId}")
    AccountDTO updateAccount(@PathVariable Long accountId, @RequestBody AccountDTO account);

//    @GetMapping("/accounts/{userId}/transactions")
//    List<TransactionDTO> getTransactionsByAccount(@PathVariable Long userId, @RequestParam(required = false) Integer limit);
//
//    @PostMapping("/accounts/{accountId}/card")
//    void createAccountCard(@RequestBody CardDTO card, @RequestParam Long accountId);
//
//    @GetMapping("/accounts/{accountId}/card")
//    List<CardDTO> getCardsByAccount(@PathVariable Long accountId);
//
//    @GetMapping("/accounts/{accountId}/cards/{cardId}")
//    CardDTO getCardById(@PathVariable Long accountId,@PathVariable Long cardId);
//
//    @DeleteMapping("/accounts/{accountId}/cards/{cardId}")
//    void deleteCard(@PathVariable Long accountId, @PathVariable Long cardId);

}
