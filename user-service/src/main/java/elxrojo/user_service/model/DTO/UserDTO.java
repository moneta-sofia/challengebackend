package elxrojo.user_service.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String nombres;
    private String apellidos;
    private Long dni;
    private String email;
    private Long telefono;
    private String cvu;
    private String alias;
    private String contrasena;
}
