package elxrojo.transaction_service.repository;

import elxrojo.transaction_service.model.Transaction;

import java.util.Optional;

public interface ITransactionRepositoryCustom {
    Optional<Transaction> findByAccountId(Long accountId);

}
