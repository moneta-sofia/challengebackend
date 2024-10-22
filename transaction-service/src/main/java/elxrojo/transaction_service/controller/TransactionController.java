package elxrojo.transaction_service.controller;

import elxrojo.transaction_service.model.DTO.TransactionDTO;
import elxrojo.transaction_service.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    ITransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestParam Float amount,
                                   @RequestParam int transactionType,
                                   @RequestParam String origin,
                                   @RequestParam String name,
                                   @RequestParam String destination,
                                   @RequestParam Long accountId) {
        transactionService.createTransaction(amount, transactionType, origin, name, destination, accountId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionByAccountId(@PathVariable Long accountId,
                                                                          @RequestParam(required = false) Integer limit) {
        int defaultLimit = (limit != null) ? limit : 50;
        return ResponseEntity.ok(transactionService.getTransactionsByAccount(accountId, defaultLimit));
    }
}
