package elxrojo.account_service.service.implementation;

import elxrojo.account_service.exception.CustomException;
import elxrojo.account_service.model.Account;
import elxrojo.account_service.repository.IAccountRepository;
import elxrojo.account_service.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public Long createAccount(String alias, String cvu, Long userId) {
        Account account = accountRepository.save(new Account(0.0F, alias, cvu, userId));
        Account savedAccount = accountRepository.save(account);
        return savedAccount.getId();

    }

    @Override
    public Float getBalance(Long userId) {
        try {
            Optional<Account> account = accountRepository.findByUserId(userId);
            float balance;

            if (account.isPresent()) {
                balance = account.get().getBalance();
            } else {
                throw new CustomException("Account not found", HttpStatus.NOT_FOUND);
            }
            return balance;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get balance: " + e.getMessage());
        }
    }

}
