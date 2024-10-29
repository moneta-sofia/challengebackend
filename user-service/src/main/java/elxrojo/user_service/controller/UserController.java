package elxrojo.user_service.controller;

import elxrojo.user_service.model.DTO.AccountDTO;
import elxrojo.user_service.model.DTO.UserDTO;
import elxrojo.user_service.model.User;
import elxrojo.user_service.model.UserWithTokenResponse;
import elxrojo.user_service.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    IUserService userService;


//    User endpoints

    @PostMapping("/register")
    public ResponseEntity<UserWithTokenResponse> RegisterUser(@RequestBody UserDTO userDTO, HttpServletRequest request) throws IOException {
        UserWithTokenResponse results = userService.signup(userDTO);
        UserDTO userCreated = results.getUserDTO();
        String token = (String) results.getAccessToken();

        User userResponse = new User(
                userCreated.getFirstName(),
                userCreated.getLastName(),
                userCreated.getDni(),
                userCreated.getEmail(),
                userCreated.getPhone());

        return ResponseEntity.ok(new UserWithTokenResponse(userResponse, token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserBySub(@PathVariable String id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{sub}")
    public ResponseEntity<UserDTO> updateUserBySub(@RequestBody UserDTO userDTO, @PathVariable String sub){
        UserDTO userUpdated = userService.updateUser(userDTO, sub);
        return ResponseEntity.ok(userUpdated);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData) {
        String token = userService.login(loginData.get("email"), loginData.get("password"));
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("token", token);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        userService.logout(token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

//
////    Accounts endpoints
//
//    @GetMapping("/{userSub}/accounts")
//    public ResponseEntity<AccountDTO> getAccountByUser(@PathVariable String userSub) {
//        return ResponseEntity.ok(userService.getAccountByUser(userSub));
//    }
//
//    @PutMapping("/{userSub}/accounts")
//    public ResponseEntity<AccountDTO> updateAccountByUser(@RequestBody AccountDTO accountDTO, @PathVariable String userSub){
//        AccountDTO accountUpdated = userService.updateAccount(userSub, accountDTO);
//        return ResponseEntity.ok(accountUpdated);
//    }
//
//
////    Transaction endpoints
//
//    @GetMapping("/{userSub}/activities")
//    public ResponseEntity<List<TransactionDTO>> getTransactionsByUser(@PathVariable String userSub, @RequestParam(required = false)  Integer limit) {
//        return ResponseEntity.ok(userService.getTransactionsByAccount(userSub, limit));
//    }
//
//
////    Card endpoints
//
//    @PostMapping("/{userSub}/cards")
//    public ResponseEntity<?> createAccountCard(@RequestBody CardDTO card, @PathVariable String userSub){
//        userService.createAccountCard(card,userSub);
//        return ResponseEntity.ok(HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{userSub}/cards/{cardId}")
//    public ResponseEntity<CardDTO> getCardById(@PathVariable String userSub, @PathVariable Long cardId){
//        return ResponseEntity.ok(userService.getCardById(userSub,cardId));
//    }
//
//    @GetMapping("/{userSub}/cards")
//    public ResponseEntity<List<CardDTO>>  getCardsByAccount(@PathVariable String userSub){
//        return ResponseEntity.ok(userService.getCardsByAccount(userSub));
//    }
//
//    @DeleteMapping("/{userSub}/cards/{cardId}")
//    public ResponseEntity<?> deleteCard(@PathVariable String userSub, @PathVariable Long cardId){
//        userService.deleteCard(userSub,cardId);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }

}
