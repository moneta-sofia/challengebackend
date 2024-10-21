package elxrojo.transaction_service.controller;

import elxrojo.transaction_service.model.DTO.TransactionDTO;
import elxrojo.transaction_service.service.ITransactionService;
import jakarta.ws.rs.POST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
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
    public ResponseEntity<TransactionDTO> getTransactionByAccountID(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getTansactionsByAccount(accountId));
    }
}
