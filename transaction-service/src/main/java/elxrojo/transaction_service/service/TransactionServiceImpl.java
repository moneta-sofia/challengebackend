package elxrojo.transaction_service.service;

import elxrojo.transaction_service.exception.CustomException;
import elxrojo.transaction_service.model.DTO.TransactionDTO;
import elxrojo.transaction_service.model.Transaction;
import elxrojo.transaction_service.model.TransactionType;
import elxrojo.transaction_service.repository.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private ITransactionRepository transactionRepository;

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
        return null;
    }
}
