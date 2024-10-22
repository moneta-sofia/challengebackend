package elxrojo.account_service.service;

import elxrojo.account_service.model.Account;
import elxrojo.account_service.model.DTO.AccountDTO;
import elxrojo.account_service.model.DTO.TransactionDTO;

import java.util.List;

public interface IAccountService {
    Long createAccount(String alias, String cvu, Long userId, String Name);
    AccountDTO getAccountByUser(Long userId);
    List<TransactionDTO> getTransactionById(Long id, int limit);
}
