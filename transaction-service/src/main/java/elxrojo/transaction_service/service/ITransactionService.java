package elxrojo.transaction_service.service;

import elxrojo.transaction_service.model.DTO.TransactionDTO;

import java.util.List;

public interface ITransactionService {
    TransactionDTO createTransaction(Float amount, Integer activityType, int transactionType, String origin, String name, String destination, Long accountId);
    List<TransactionDTO> getTransactionsByAccount(Long accountId, int limit);
    TransactionDTO getTransactionByAccount(Long accountId, Long transactionId);
    List<TransactionDTO> getLatestDestinations(Long accountId);
}
