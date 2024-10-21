package elxrojo.account_service.controller;

import elxrojo.account_service.model.DTO.AccountDTO;
import elxrojo.account_service.model.DTO.TransactionDTO;
import elxrojo.account_service.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    IAccountService accountService;

    @PostMapping("/create")
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

    @GetMapping("/{userId}/transactions")
    public ResponseEntity<TransactionDTO> getTransactionsByAccount(@PathVariable Long userId){
        return ResponseEntity.ok(accountService.getTransactionById(userId));
    }
}
