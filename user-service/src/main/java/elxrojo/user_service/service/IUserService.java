package elxrojo.user_service.service;

import elxrojo.user_service.model.DTO.AccountDTO;
import elxrojo.user_service.model.DTO.UserDTO;
import elxrojo.user_service.model.UserWithTokenResponse;


import java.io.IOException;

public interface IUserService {
    UserWithTokenResponse signup(UserDTO user) throws IOException;
    String login(String email, String password);
    UserDTO getUserById(String id);
    void logout(String token);
    AccountDTO getAccountByUser(String userSub);
}
