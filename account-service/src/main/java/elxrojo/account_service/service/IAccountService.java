package elxrojo.account_service.service;

import elxrojo.account_service.model.Account;
import elxrojo.account_service.model.DTO.AccountDTO;
import elxrojo.account_service.model.DTO.CardDTO;
import elxrojo.account_service.model.DTO.TransactionDTO;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    List<AccountDTO> getAllAccounts();
    Long createAccount(String alias, String cvu, String userId, String Name);
    AccountDTO getAccountByUser(String userId);
    AccountDTO updateAccount(Long accountId, AccountDTO accountUpdated);

    TransactionDTO createDeposit(Float amount, String userId);
    TransactionDTO createTransaction(Float amount, String destination, String userId);
    List<TransactionDTO> getTransactionsById(String id, Integer limit);
    TransactionDTO getTransactionById(String userId, Long transactionId);
    List<AccountDTO> getLatestDestinations(String userId);

    void createAccountCard(CardDTO card, String userId);
    List<CardDTO> getCardsByAccount(String userId);
    CardDTO getCardById( String userId, Long cardId);
    void deleteCard(String userId, Long cardId);

}
