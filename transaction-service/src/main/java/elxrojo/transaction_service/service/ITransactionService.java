package elxrojo.transaction_service.service;

import elxrojo.transaction_service.model.DTO.TransactionDTO;

import java.util.List;

public interface ITransactionService {
    void createTransaction(Float amount, int transactionType, String origin, String name, String destination, Long accountId);
    List<TransactionDTO> getTransactionsByAccount(Long accountId, int limit);
}
