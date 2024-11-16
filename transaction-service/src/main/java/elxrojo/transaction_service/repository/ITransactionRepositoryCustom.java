package elxrojo.transaction_service.repository;

import elxrojo.transaction_service.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITransactionRepositoryCustom {
    List<Transaction> findByAccountIdOrderByDatedDesc(Long accountId, Pageable pageable);
    @Query("SELECT t FROM Transaction t WHERE t.id IN (SELECT MAX(sub.id) FROM Transaction sub WHERE sub.accountId = :accountId AND sub.activityType = 'transferOut' GROUP BY sub.destination) ORDER BY t.dated DESC")
    List<Transaction> getLatestDestinations(Long accountId);

}
