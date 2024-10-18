package elxrojo.user_service.repository;

import org.springframework.stereotype.Repository;
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
}
