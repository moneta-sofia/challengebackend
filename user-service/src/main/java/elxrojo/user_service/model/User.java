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

    @Column(name = "accountId", nullable = false)
    private Long accountId;


    public User(String firstName, String lastName, Long dni, String email, Long accountId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.email = email;
        this.accountId = accountId;
    }

    public User(Long id, String firstName, String lastName, Long dni, String email, Long telefono, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.email = email;
    }

    public User() {
    }

}
