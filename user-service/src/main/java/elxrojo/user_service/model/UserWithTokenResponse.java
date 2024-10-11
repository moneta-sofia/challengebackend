package elxrojo.user_service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import elxrojo.user_service.model.DTO.UserDTO;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserWithTokenResponse {

    private UserDTO userDTO;

    private User user;
    private String accesToken;


    public UserWithTokenResponse(UserDTO userDTO, String accesToken) {
        this.userDTO = userDTO;
        this.accesToken = accesToken;
    }

    public UserWithTokenResponse(User user, String accesToken) {
        this.user = user;
        this.accesToken = accesToken;
    }


}
