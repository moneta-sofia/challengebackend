package elxrojo.transaction_service.repository;

import elxrojo.transaction_service.model.Transaction;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITransactionRepositoryCustom {
    List<Transaction> findByAccountIdOrderByDatedDesc(Long accountId, Pageable pageable);

}
