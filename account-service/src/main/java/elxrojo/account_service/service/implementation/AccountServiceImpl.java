package elxrojo.account_service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import elxrojo.account_service.exception.CustomException;
import elxrojo.account_service.model.Account;
import elxrojo.account_service.model.DTO.AccountDTO;
import elxrojo.account_service.model.DTO.TransactionDTO;
import elxrojo.account_service.repository.IAccountMapper;
import elxrojo.account_service.repository.IAccountRepository;
import elxrojo.account_service.repository.TransactionRepository;
import elxrojo.account_service.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IAccountMapper accountMapper;

    ObjectMapper mapper = new ObjectMapper();


    @Override
    public Long createAccount(String alias, String cvu, Long userId, String name) {
        Account account = accountRepository.save(new Account(name, 0.0F, alias, cvu, userId));
        Account savedAccount = accountRepository.save(account);
        return savedAccount.getId();

    }

    @Override
    public AccountDTO getAccountByUser(Long userId) {
        try {
            Optional<Account> account = accountRepository.findByUserId(userId);
            if (account.isPresent()) {
                AccountDTO accountDTO = mapper.convertValue(account.get(), AccountDTO.class);
                return accountDTO;
            } else {
                throw new CustomException("Account not found", HttpStatus.NOT_FOUND);
            }

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get balance: " + e.getMessage());
        }
    }


    @Override
    public AccountDTO updateAccount(Long accountId, AccountDTO accountUpdated) {
        try {
            Account account = accountRepository.findById(accountId).orElseThrow(() -> new CustomException("Account not found", HttpStatus.NOT_FOUND));
            accountMapper.updateAccount(accountUpdated, account);
            accountRepository.save(account);
            AccountDTO accountDTO = mapper.convertValue(account, AccountDTO.class);
            return accountDTO;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get balance: " + e.getMessage());
        }
    }


    @Override
    public List<TransactionDTO> getTransactionById(Long id, Integer limit) {
        try {
            return transactionRepository.getTransactionByAccountId(id, limit);
        } catch (CustomException e) {
            throw e;
        }

    }

}
