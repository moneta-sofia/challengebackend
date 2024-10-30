package elxrojo.user_service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "dni", length = 8, columnDefinition = "char(8)")
    private Long dni;

    @Column
    private String password;

    @Column(name = "phone", length = 10, columnDefinition = "char(10)")
    private Long phone;

    @Column
    private String email;

    @Column(name = "accountId", nullable = true)
    private Long accountId;


    public User(String firstName, String lastName, Long dni, String email, Long accountId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.email = email;
        this.accountId = accountId;
    }

    public User(String id, String firstName, String lastName, Long dni, String email, Long telefono, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.email = email;
    }

    public User() {
    }

}
