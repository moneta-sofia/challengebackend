package elxrojo.account_service.service.implementation;

import elxrojo.account_service.model.Account;
import elxrojo.account_service.repository.IAccountRepository;
import elxrojo.account_service.service.IAccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {
    private IAccountRepository accountRepository;

    @Override
    public void createAccount(String alias, String cvu, Long userId) {
        accountRepository.save(new Account(0.0F,alias,cvu,userId));
    }

}
