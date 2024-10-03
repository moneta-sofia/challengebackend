package elxrojo.user_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column
    private String nombres;
    @Column
    private String apellidos;

    @Column(name = "dni", length = 8, columnDefinition = "char(8)")
    private Long dni;

    @Column
    private String email;

    @Column(name = "telefono", length = 10, columnDefinition = "char(10)")
    private Long telefono;

    @Column
    private String contrasena;

    @Column(name = "cvu", length = 22, columnDefinition = "char(22)")
    private String cvu;
    @Column
    private String alias;


    public Usuario(Long id, String nombres, String apellidos, Long dni, String email, Long telefono, String contrasena) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.contrasena = contrasena;
    }
}
