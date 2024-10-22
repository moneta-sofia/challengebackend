package elxrojo.user_service.repository;

import elxrojo.user_service.model.DTO.AccountDTO;
import elxrojo.user_service.model.DTO.TransactionDTO;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public class AccountRepository {
    private  IFeignAccountRepository feignAccountRepository;

    public AccountRepository(IFeignAccountRepository feignAccountRepository) {
        this.feignAccountRepository = feignAccountRepository;
    }

    public Long createAccount(@RequestBody AccountDTO accountDTO){
        return feignAccountRepository.createAccount(accountDTO);
    }

    public AccountDTO getAccountByUser(@PathVariable Long userId){
        return feignAccountRepository.getAccountByUser(userId);
    }


    public List<TransactionDTO> getTransactionsByAccount(@PathVariable Long userId, int limit){
        return feignAccountRepository.getTransactionsByAccount(userId, limit);
    }
}
