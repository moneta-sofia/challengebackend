package elxrojo.user_service.repository;

import elxrojo.user_service.model.DTO.AccountDTO;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public class AccountRepository {
    private  IFeignAccountRepository feignAccountRepository;

    public AccountRepository(IFeignAccountRepository feignAccountRepository) {
        this.feignAccountRepository = feignAccountRepository;
    }

    public Long createAccount(@RequestParam String alias,
                              @RequestParam String cvu,
                              @RequestParam Long userId){
        return feignAccountRepository.createAccount(alias, cvu, userId);
    }


    public AccountDTO getAccountByUser(@PathVariable Long userId){
        return feignAccountRepository.getAccountByUser(userId);
    }

}
