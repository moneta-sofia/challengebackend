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

    @PostMapping("/")
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
    public ResponseEntity<UserWithTokenResponse> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PatchMapping("/{sub}")
    public ResponseEntity<UserDTO> updateUserBySub(@RequestBody UserDTO userDTO, @PathVariable String userId) {
        UserDTO userUpdated = userService.updateUser(userDTO, userId);
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
}
