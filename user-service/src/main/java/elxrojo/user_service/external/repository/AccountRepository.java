package elxrojo.user_service.external.repository;

import elxrojo.user_service.external.feign.IFeignAccountRepository;
import elxrojo.user_service.model.DTO.AccountDTO;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Repository
public class AccountRepository {
    private IFeignAccountRepository feignAccountRepository;

    public AccountRepository(IFeignAccountRepository feignAccountRepository) {
        this.feignAccountRepository = feignAccountRepository;
    }

    public Long createAccount(@RequestBody AccountDTO accountDTO) {
        return feignAccountRepository.createAccount(accountDTO);
    }

    public AccountDTO updateAccount(@PathVariable Long accountId, @RequestBody AccountDTO account) {
        return feignAccountRepository.updateAccount(accountId, account);
    }

}
