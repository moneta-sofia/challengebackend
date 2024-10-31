package elxrojo.account_service.service;

import elxrojo.account_service.model.Account;
import elxrojo.account_service.model.DTO.AccountDTO;
import elxrojo.account_service.model.DTO.CardDTO;
import elxrojo.account_service.model.DTO.TransactionDTO;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    Long createAccount(String alias, String cvu, String userId, String Name);
    AccountDTO getAccountByUser(String userId);
    AccountDTO updateAccount(Long accountId, AccountDTO accountUpdated);

    List<TransactionDTO> getTransactionsById(Long id, Integer limit);
    TransactionDTO getTransactionById(Long accountId, Long transactionId);

    void createAccountCard(CardDTO card, Long accountId);
    List<CardDTO> getCardsByAccount(Long accountId);
    CardDTO getCardById(Long cardId, Long accountId);
    void deleteCard(Long accountId, Long cardId);

}
