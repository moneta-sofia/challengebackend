package elxrojo.account_service.external.repository;

import elxrojo.account_service.model.DTO.TransactionDTO;
import elxrojo.account_service.external.feign.IFeignTransactionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public class TransactionRepository {

    private final IFeignTransactionRepository transactionRepository;

    public TransactionRepository(IFeignTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionDTO> getTransactionByAccountId(@RequestParam Long accountId, @RequestParam(required = false) Integer limit) {
        return transactionRepository.getTransactionByAccountId(accountId, limit);
    }

}
