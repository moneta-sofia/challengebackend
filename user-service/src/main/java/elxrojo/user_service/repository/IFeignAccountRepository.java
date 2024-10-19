package elxrojo.user_service.repository;

import elxrojo.user_service.model.DTO.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name= "account-service", url= "http://localhost:8085")
 public interface IFeignAccountRepository {

    @PostMapping("/account/create")
    Long createAccount(@RequestParam String alias,
                       @RequestParam String cvu,
                       @RequestParam Long userId);

    @GetMapping("/account/{userId}")
    AccountDTO getAccountByUser(@PathVariable Long userId);

}
