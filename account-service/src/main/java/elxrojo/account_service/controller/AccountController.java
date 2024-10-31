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

    @GetMapping("/{userId}/activity/{transactionId}")
    public ResponseEntity<TransactionDTO> getTransactionByAccount(@PathVariable String userId, @PathVariable Long transactionId){
        if (userId == null) {
            throw new CustomException("Invalid user ID!", HttpStatus.BAD_REQUEST);
        }

        if (transactionId == null || transactionId <= 0) {
            throw new CustomException("Invalid transaction ID!", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(accountService.getTransactionById(userId, transactionId));
    }


//    Card endpoints

    @PostMapping("/{userId}/card")
    public ResponseEntity<?> createAccountCard(@RequestBody CardDTO card, @PathVariable String userId) {
        accountService.createAccountCard(card, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}/cards/{cardId}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable String userId, @PathVariable Long cardId) {
        return ResponseEntity.ok(accountService.getCardById(userId, cardId));
    }


    @GetMapping("/{userId}/card")
    public ResponseEntity<List<CardDTO>> getCardsByAccount(@PathVariable String userId) {
        return ResponseEntity.ok(accountService.getCardsByAccount(userId));
    }

    @DeleteMapping("/{userId}/cards/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable String userId, @PathVariable Long cardId) {
        accountService.deleteCard(userId, cardId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
