package elxrojo.user_service.external.repository;

import elxrojo.user_service.external.feign.IFeignAccountRepository;
import elxrojo.user_service.model.DTO.AccountDTO;
import elxrojo.user_service.model.DTO.CardDTO;
import elxrojo.user_service.model.DTO.TransactionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public class AccountRepository {
    private IFeignAccountRepository feignAccountRepository;

    public AccountRepository(IFeignAccountRepository feignAccountRepository) {
        this.feignAccountRepository = feignAccountRepository;
    }

    public Long createAccount(@RequestBody AccountDTO accountDTO){
        return feignAccountRepository.createAccount(accountDTO);
    }



    public AccountDTO getAccountByUser(@PathVariable Long userId){
        return feignAccountRepository.getAccountByUser(userId);
    }

    public AccountDTO updateAccount(@PathVariable Long accountId, @RequestBody AccountDTO account){
        return feignAccountRepository.updateAccount(accountId, account);
    }



    public List<TransactionDTO> getTransactionsByAccount(@PathVariable Long userId,@RequestParam(required = false)  Integer limit){
        return feignAccountRepository.getTransactionsByAccount(userId, limit);
    }


    public void createAccountCard(@RequestBody CardDTO card,Long accountId){
        feignAccountRepository.createAccountCard(card, accountId);
    }

    public List<CardDTO> getCardsByAccount(@PathVariable Long accountId){
        return feignAccountRepository.getCardsByAccount(accountId);
    }

    public CardDTO getCardById(@PathVariable Long accountId,@PathVariable Long cardId){
        return feignAccountRepository.getCardById(accountId, cardId);
    }

}
