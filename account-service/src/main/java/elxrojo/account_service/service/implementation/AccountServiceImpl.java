package elxrojo.account_service.service.implementation;

import elxrojo.account_service.model.Account;
import elxrojo.account_service.repository.IAccountRepository;
import elxrojo.account_service.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public Long createAccount(String alias, String cvu, Long userId) {
        Account account = accountRepository.save(new Account(0.0F,alias,cvu,userId));
        Account savedAccount = accountRepository.save(account);
        return savedAccount.getId();

    }

}
