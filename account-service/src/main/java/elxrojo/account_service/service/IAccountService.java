package elxrojo.account_service.service;

import elxrojo.account_service.model.Account;
import elxrojo.account_service.model.DTO.AccountDTO;
import elxrojo.account_service.model.DTO.CardDTO;
import elxrojo.account_service.model.DTO.TransactionDTO;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    Long createAccount(String alias, String cvu, Long userId, String Name);
    AccountDTO getAccountByUser(Long userId);
    AccountDTO updateAccount(Long accountId, AccountDTO accountUpdated);

    List<TransactionDTO> getTransactionById(Long id, Integer limit);


    void createAccountCard(CardDTO card, Long accountId);
    List<CardDTO> getCardsByAccount(Long accountId);
    CardDTO getCardById(Long cardId, Long accountId);
    void deleteCard(Long accountId, Long cardId);

}
