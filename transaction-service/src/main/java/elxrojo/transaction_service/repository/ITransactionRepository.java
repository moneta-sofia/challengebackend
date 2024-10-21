package elxrojo.transaction_service.repository;

import elxrojo.transaction_service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long>, ITransactionRepositoryCustom {
}
