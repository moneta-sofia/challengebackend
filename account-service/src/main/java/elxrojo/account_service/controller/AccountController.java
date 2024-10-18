package elxrojo.account_service.controller;


import elxrojo.account_service.model.Account;
import elxrojo.account_service.service.IAccountService;
import elxrojo.account_service.service.implementation.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    IAccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Long> createAccount(@RequestParam String alias,
                                                @RequestParam String cvu,
                                                @RequestParam Long userId ){
        Long idAccountCreated = accountService.createAccount(alias,cvu ,userId);
        return ResponseEntity.ok(idAccountCreated);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<Float> getBalance(@PathVariable Long userId){
        Float balance = accountService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.ok("pong");
    }
}
