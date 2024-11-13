package elxrojo.transaction_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import elxrojo.transaction_service.exception.CustomException;
import elxrojo.transaction_service.model.ActivityType;
import elxrojo.transaction_service.model.DTO.TransactionDTO;
import elxrojo.transaction_service.model.Transaction;
import elxrojo.transaction_service.model.TransactionType;
import elxrojo.transaction_service.repository.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void createTransaction(Float amount, Integer activityType, int transactionType, String origin, String name, String destination, Long accountId) {
        if (transactionType == 1) {
            Transaction transaction = new Transaction(
                    amount,
                    activityType == 1 ? ActivityType.transferIn : ActivityType.transferOut,
                    TransactionType.transfer,
                    origin,
                    name,
                    destination,
                    LocalDateTime.now(),
                    accountId
            );
            transactionRepository.save(transaction);
        } else if (transactionType == 2) {
            Transaction transaction = new Transaction(
                    amount,
                    TransactionType.deposit,
                    LocalDateTime.now(),
                    accountId
            );
            transactionRepository.save(transaction);
        }
    }

    @Override
    public List<TransactionDTO> getTransactionsByAccount(Long accountId, int limit) {
        if (accountId == null || accountId <= 0) {
            throw new CustomException("Invalid account ID!", HttpStatus.BAD_REQUEST);
        }

        Pageable pageable = PageRequest.of(0, limit, Sort.by("dated").descending());
        List<Transaction> transactions = transactionRepository.findByAccountIdOrderByDatedDesc(accountId, pageable);
        return transactions.stream()
                .map(transaction -> mapper.convertValue(transaction, TransactionDTO.class))
                .collect(Collectors.toList());
    } //refactor, return only deposits

    @Override
    public TransactionDTO getTransactionByAccount(Long accountId, Long transactionId) {
        Optional<Transaction> foundTransaction = transactionRepository.findById(transactionId);
        if (foundTransaction.isEmpty()) {
            throw new CustomException("That activity doesn't exist!", HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(accountId, foundTransaction.get().getAccountId())) {
            throw new CustomException("Without permission!", HttpStatus.UNAUTHORIZED);
        }
        return mapper.convertValue(foundTransaction, TransactionDTO.class);
    }
}
