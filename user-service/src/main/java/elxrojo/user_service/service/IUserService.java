package elxrojo.user_service.service;

import elxrojo.user_service.model.DTO.UserDTO;

import java.io.IOException;

public interface IUserService {
    UserDTO signup(UserDTO user) throws IOException;
    UserDTO login(String email, String password);
}
