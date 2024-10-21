package elxrojo.account_service.service;

import elxrojo.account_service.model.Account;
import elxrojo.account_service.model.DTO.AccountDTO;
import elxrojo.account_service.model.DTO.TransactionDTO;

public interface IAccountService {
    Long createAccount(String alias, String cvu, Long userId, String Name);
    AccountDTO getAccountByUser(Long userId);
    TransactionDTO getTransactionById(Long id);
}
