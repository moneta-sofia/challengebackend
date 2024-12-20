package elxrojo.transaction_service.controller;

import elxrojo.transaction_service.exception.CustomException;
import elxrojo.transaction_service.model.DTO.TransactionDTO;
import elxrojo.transaction_service.service.ITransactionService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    ITransactionService transactionService;



    @PostMapping("/create")
    public ResponseEntity<TransactionDTO> create(@RequestParam Float amount,
                                    @RequestParam(required = false) Integer activityType,
                                    @RequestParam int transactionType,
                                    @RequestParam(required = false) String origin ,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String destination,
                                    @RequestParam Long accountId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(amount, activityType, transactionType, origin, name, destination, accountId));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionByAccountId(@PathVariable Long accountId,
                                                                          @RequestParam(required = false) Integer limit) {

        if (accountId == null || accountId <= 0) {
            throw new CustomException("Invalid account ID!", HttpStatus.BAD_REQUEST);
        }
        int defaultLimit = (limit != null) ? limit : 50;
        return ResponseEntity.ok(transactionService.getTransactionsByAccount(accountId, defaultLimit));
    }

    @GetMapping("/{transactionId}/account/{accountId}")
    public ResponseEntity<TransactionDTO> getTransactionByAccount(@PathVariable Long accountId, @PathVariable Long transactionId) {
        if (accountId == null || accountId <= 0) {
            throw new CustomException("Invalid account ID!", HttpStatus.BAD_REQUEST);
        }
        if (transactionId == null || transactionId <= 0) {
            throw new CustomException("Invalid transaction ID!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(transactionService.getTransactionByAccount(accountId, transactionId));
    }

    @GetMapping("/{accountId}/destination")
    public ResponseEntity<List<TransactionDTO>>  getLatestDestinations(@PathVariable Long accountId) {
        if (accountId == null || accountId <= 0) {
            throw new CustomException("Invalid account ID!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(transactionService.getLatestDestinations(accountId));
    }
}
