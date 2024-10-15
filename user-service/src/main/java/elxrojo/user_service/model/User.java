package elxrojo.user_service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Column
    private String firstName;
    @Column
    private String lastName;

    @Column(name = "dni", length = 8, columnDefinition = "char(8)")
    private Long dni;

    @Column
    private String email;

    @Column(name = "phone", length = 10, columnDefinition = "char(10)")
    private Long phone;

    @Column
    private String password;

    @Column(name = "cvu", length = 22, columnDefinition = "char(22)")
    private String cvu;
    @Column
    private String alias;


    public User(Long id, String firstName, String lastName, Long dni, String email, Long telefono, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.email = email;
        this.phone = telefono;
        this.password = password;
    }

    public User() {
    }

    public User(String firstName, String lastName, Long dni, String email, Long telefono, String cvu, String alias) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.email = email;
        this.phone = telefono;
        this.cvu = cvu;
        this.alias = alias;
    }

}
