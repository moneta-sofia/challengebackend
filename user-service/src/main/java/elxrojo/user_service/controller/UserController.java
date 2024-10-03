package elxrojo.user_service.controller;

import elxrojo.user_service.model.DTO.UserDTO;
import elxrojo.user_service.service.IUserService;
import elxrojo.user_service.service.Implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/signup")
    public UserDTO getAllUsers() throws IOException {
        return userService.signup(null);
    }
}
