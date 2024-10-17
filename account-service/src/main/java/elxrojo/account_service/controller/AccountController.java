package elxrojo.account_service.controller;


import elxrojo.account_service.service.IAccountService;
import elxrojo.account_service.service.implementation.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    IAccountService accountService;

    @PostMapping("/")
    public ResponseEntity<String> createAccount(){

        return ResponseEntity.ok("Account created successfully");
    }
}
