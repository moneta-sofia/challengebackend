package elxrojo.account_service.repository;

import elxrojo.account_service.model.DTO.TransactionDTO;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public class TransactionRepository {

    private IFeignTransactionRepository transactionRepository;

    public TransactionRepository(IFeignTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionDTO getTransactionByAccountId(@RequestParam Long accountId) {
        return transactionRepository.getTransactionByAccountId(accountId);
    }

}
