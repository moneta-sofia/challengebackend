package elxrojo.transaction_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import elxrojo.transaction_service.exception.CustomException;
import elxrojo.transaction_service.model.DTO.TransactionDTO;
import elxrojo.transaction_service.model.Transaction;
import elxrojo.transaction_service.model.TransactionType;
import elxrojo.transaction_service.repository.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

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
    public TransactionDTO getTansactionsByAccount(Long accountId) {
        try {
            Optional<Transaction> transaction = transactionRepository.findByAccountId(accountId);
            if (transaction.isPresent()) {
                return mapper.convertValue(transaction.get(), TransactionDTO.class);
            } else {
                throw new CustomException("Transaction not found", HttpStatus.NOT_FOUND);
            }
        } catch (CustomException e) {
            throw e;
        }
    }
}
