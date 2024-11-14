package elxrojo.account_service.external.repository;

import elxrojo.account_service.model.DTO.TransactionDTO;
import elxrojo.account_service.external.feign.IFeignTransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public class TransactionRepository {

    private final IFeignTransactionRepository transactionRepository;

    public TransactionRepository(IFeignTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionDTO> getTransactionsByAccount(@RequestParam Long accountId, @RequestParam(required = false) Integer limit) {
        return transactionRepository.getTransactionsByAccount(accountId, limit);
    }

    public TransactionDTO getTransactionByAccount(@PathVariable Long accountId, @PathVariable Long transactionId) {
        return transactionRepository.getTransactionByAccount(accountId, transactionId);
    }

    public ResponseEntity<?> create(@RequestParam Float amount,
                                    @RequestParam(required = false) Integer activityType,
                                    @RequestParam int transactionType,
                                    @RequestParam(required = false) String origin,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String destination,
                                    @RequestParam Long accountId) {
        return transactionRepository.create(amount, activityType, transactionType, origin, name, destination, accountId);
    }
}
