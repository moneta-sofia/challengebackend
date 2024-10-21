package elxrojo.transaction_service.service;

import elxrojo.transaction_service.model.DTO.TransactionDTO;

public interface ITransactionService {
    void createTransaction(Float amount, int transactionType, String origin, String name, String destination, Long accountId);
    TransactionDTO getTransactionsByAccount(Long accountId);
}
