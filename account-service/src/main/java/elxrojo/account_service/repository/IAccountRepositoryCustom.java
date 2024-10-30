package elxrojo.account_service.repository;

import elxrojo.account_service.model.Account;

import java.util.Optional;

public interface IAccountRepositoryCustom {

    Optional<Account> findByUserId(String userId);
}
