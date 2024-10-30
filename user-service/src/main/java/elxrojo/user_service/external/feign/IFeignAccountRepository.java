package elxrojo.user_service.external.feign;

import elxrojo.user_service.model.DTO.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "account-service", url = "http://localhost:8085")
public interface IFeignAccountRepository {

    @PostMapping("/accounts/")
    Long createAccount(@RequestBody AccountDTO accountDTO);

    @PutMapping("/accounts/{accountId}")
    AccountDTO updateAccount(@PathVariable Long accountId, @RequestBody AccountDTO account);

}
