package elxrojo.account_service.repository;

import elxrojo.account_service.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long>, IAccountRepositoryCustom {
}
