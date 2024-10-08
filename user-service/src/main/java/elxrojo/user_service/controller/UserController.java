package elxrojo.user_service.controller;

import elxrojo.user_service.model.DTO.UserDTO;
import elxrojo.user_service.model.User;
import elxrojo.user_service.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Enumeration;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> RegisterUser(@RequestBody UserDTO userDTO, HttpServletRequest request) throws IOException {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }

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

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
