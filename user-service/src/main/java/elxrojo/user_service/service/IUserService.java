package elxrojo.user_service.service;

import elxrojo.user_service.model.DTO.UserDTO;
import elxrojo.user_service.model.UserWithTokenResponse;


import java.io.IOException;

public interface IUserService {
    UserWithTokenResponse signup(UserDTO user) throws IOException;
    UserDTO login(String email, String password);
}
