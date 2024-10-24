package elxrojo.account_service.controller;

import elxrojo.account_service.model.DTO.AccountDTO;
import elxrojo.account_service.model.DTO.CardDTO;
import elxrojo.account_service.model.DTO.TransactionDTO;
import elxrojo.account_service.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    IAccountService accountService;

    @PostMapping("/")
    public ResponseEntity<Long> createAccount(@RequestBody AccountDTO account ){
        Long idAccountCreated = accountService.createAccount(
                account.getAlias(),
                account.getCvu() ,
                account.getUserId(),
                account.getName()
        );
        return ResponseEntity.ok(idAccountCreated);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<AccountDTO> getAccountByUser(@PathVariable Long userId){
        AccountDTO account = accountService.getAccountByUser(userId);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long accountId, @RequestBody AccountDTO account){
        return ResponseEntity.ok(accountService.updateAccount(accountId, account));
    }

//    Transaction endpoints

    @GetMapping("/{userId}/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccount(@PathVariable Long userId,
                                                                         @RequestParam(required = false) Integer limit){
        return ResponseEntity.ok(accountService.getTransactionById(userId, limit));
    }


//    Card endpoints
    @PostMapping("/card")
    public ResponseEntity<?> createAccountCard(@RequestBody CardDTO card){
        accountService.createAccountCard(card);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

}
