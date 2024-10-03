package elxrojo.user_service.controller;

import elxrojo.user_service.model.DTO.UserDTO;
import elxrojo.user_service.model.User;
import elxrojo.user_service.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
//@RequestMapping("/users")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> RegisterUser(@RequestBody UserDTO userDTO) throws IOException {
        UserDTO userRegistered = userService.signup(userDTO);
        User userResponse = new User(
                userRegistered.getFirstName(),
                userRegistered.getLastName(),
                userRegistered.getDni(),
                userRegistered.getEmail(),
                userRegistered.getPhone(),
                userRegistered.getCvu(),
                userRegistered.getAlias());
        return ResponseEntity.ok(userResponse) ;
    }
}
