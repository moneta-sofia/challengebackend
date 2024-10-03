package elxrojo.user_service.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Long dni;
    private String email;
    private Long phone;
    private String cvu;
    private String alias;
    private String password;
}
