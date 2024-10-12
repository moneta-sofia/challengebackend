package elxrojo.user_service.controller;

import elxrojo.user_service.model.DTO.UserDTO;
import elxrojo.user_service.model.User;
import elxrojo.user_service.model.UserWithTokenResponse;
import elxrojo.user_service.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    IUserService userService;

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
                userCreated.getPhone(),
                userCreated.getCvu(),
                userCreated.getAlias());

        return ResponseEntity.ok(new UserWithTokenResponse(userResponse, token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> ping(@PathVariable String id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData) {
        String token = userService.login(loginData.get("email"), loginData.get("password"));
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("token", token);
        return ResponseEntity.ok(response);

    }

}
