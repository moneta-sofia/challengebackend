package elxrojo.user_service.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "account-service", url= "http://localhost:8085")
 public interface IFeignAccountRepository {

    @PostMapping("/account/create")
    Long createAccount(@RequestParam String alias,
                       @RequestParam String cvu,
                       @RequestParam Long userId);
}
