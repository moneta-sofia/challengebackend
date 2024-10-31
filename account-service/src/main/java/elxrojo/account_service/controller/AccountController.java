package elxrojo.account_service.controller;

import com.auth0.jwt.JWT;
import elxrojo.account_service.exception.CustomException;
import elxrojo.account_service.model.DTO.AccountDTO;
import elxrojo.account_service.model.DTO.CardDTO;
import elxrojo.account_service.model.DTO.TransactionDTO;
import elxrojo.account_service.service.IAccountService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    IAccountService accountService;

    @PostMapping("/")
    public ResponseEntity<Long> createAccount(@RequestBody AccountDTO account) {
        Long idAccountCreated = accountService.createAccount(
                account.getAlias(),
                account.getCvu(),
                account.getUserId(),
                account.getName()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(idAccountCreated);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<AccountDTO> getAccountByUser(@PathVariable String userId) {
        AccountDTO account = accountService.getAccountByUser(userId);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long accountId, @RequestBody AccountDTO account) {
        return ResponseEntity.ok(accountService.updateAccount(accountId, account));
    }


//    Transaction endpoints

    @PostMapping("{userId}/transferences")
    public ResponseEntity<?> createTranference(@PathVariable String userId,
                                               @RequestParam Float amount,
                                               @RequestParam int transactionType,
                                               @RequestParam String destination,
                                               @RequestHeader("Authorization") String barerToken){

        String idFromToken = JWT.decode(barerToken.substring("Bearer ".length())).getSubject();
        if (!idFromToken.equals(userId)){
            throw new CustomException("Without permission to do this action", HttpStatus.FORBIDDEN);
        }
        accountService.createTransaction(amount,transactionType,destination,userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/{userId}/activity")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccount(@PathVariable String userId,
                                                                         @RequestParam(required = false) Integer limit,
                                                                         @RequestHeader("Authorization") String barerToken) {
        if (userId == null) {
            throw new CustomException("Invalid user ID!", HttpStatus.BAD_REQUEST);
        }
        String idFromToken = JWT.decode(barerToken.substring("Bearer ".length())).getSubject();
        if (!idFromToken.equals(userId)){
            throw new CustomException("Without permission to see this info", HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(accountService.getTransactionsById(userId, limit));
    }

    @GetMapping("/{accountId}/activity/{transactionId}")
    public ResponseEntity<TransactionDTO> getTransactionByAccount(@PathVariable Long accountId, @PathVariable Long transactionId){
        if (accountId == null || accountId <= 0) {
            throw new CustomException("Invalid account ID!", HttpStatus.BAD_REQUEST);
        }

        if (transactionId == null || transactionId <= 0) {
            throw new CustomException("Invalid transaction ID!", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(accountService.getTransactionById(accountId, transactionId));
    }


//    Card endpoints

    @PostMapping("/{accountId}/card")
    public ResponseEntity<?> createAccountCard(@RequestBody CardDTO card, @PathVariable Long accountId) {
        accountService.createAccountCard(card, accountId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{accountId}/cards/{cardId}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable Long accountId, @PathVariable Long cardId) {
        return ResponseEntity.ok(accountService.getCardById(accountId, cardId));
    }


    @GetMapping("/{accountId}/card")
    public ResponseEntity<List<CardDTO>> getCardsByAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getCardsByAccount(accountId));
    }

    @DeleteMapping("/{accountId}/cards/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable Long accountId, @PathVariable Long cardId) {
        accountService.deleteCard(accountId, cardId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
