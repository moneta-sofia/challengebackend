package elxrojo.account_service.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {


    @PostMapping("/")
    public ResponseEntity<String> createAccount(){
        return ResponseEntity.ok("Account created successfully");
    }
}
