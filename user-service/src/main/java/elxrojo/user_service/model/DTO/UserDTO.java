package elxrojo.user_service.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String nombres;
    private String apellidos;
    private Integer dni;
    private String email;
    private Integer telefono;
    private Integer cvu;
    private String alias;
}
