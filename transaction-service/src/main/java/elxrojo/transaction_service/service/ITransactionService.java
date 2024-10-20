package elxrojo.transaction_service.service;

import elxrojo.transaction_service.model.DTO.TransactionDTO;
import elxrojo.transaction_service.model.TransactionType;

import java.util.Date;

public interface ITransactionService {
    void createTransaction(Float amount, int transactionType, String origin, String name, String destination, Long accountId);
    TransactionDTO getTansactionsByAccount(Long accountId);
}
