package elxrojo.user_service.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private Long dni;
    private String email;
    private Long phone;
    private String password;
    private Long accountId;
}
