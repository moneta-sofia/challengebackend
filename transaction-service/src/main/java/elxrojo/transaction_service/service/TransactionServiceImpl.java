package elxrojo.transaction_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import elxrojo.transaction_service.exception.CustomException;
import elxrojo.transaction_service.model.DTO.TransactionDTO;
import elxrojo.transaction_service.model.Transaction;
import elxrojo.transaction_service.model.TransactionType;
import elxrojo.transaction_service.repository.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void createTransaction(Float amount, int transactionType, String origin, String name, String destination, Long accountId) {
        try {
            Transaction transaction = new Transaction(
                    amount,
                    (transactionType == 1 ? TransactionType.deposit : TransactionType.transfer),
                    origin,
                    name,
                    destination,
                    LocalDateTime.now(),
                    accountId);
            transactionRepository.save(transaction);
        } catch (CustomException e) {
            throw e;
        }
    }

    @Override
    public List<TransactionDTO> getTransactionsByAccount(Long accountId, int limit) {
        try {
            Pageable pageable = PageRequest.of(0, limit, Sort.by("dated").descending());
            List<Transaction> transactions = transactionRepository.findByAccountIdOrderByDatedDesc(accountId, pageable);
            return transactions.stream()
                    .map(transaction -> mapper.convertValue(transaction, TransactionDTO.class))
                    .collect(Collectors.toList());
        } catch (CustomException e) {
            throw e;
        }
    }
}
