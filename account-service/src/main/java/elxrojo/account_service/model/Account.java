package elxrojo.account_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name= "name")
    private String name;

    @Column(name = "balance")
    private Float balance;

    @Column(name = "alias")
    private String alias;

    @Column(name = "cvu", length = 22, columnDefinition = "char(22)")
    private String cvu;

    @Column(name = "userId", nullable = false)
    private String userId;


    public Account(Long id, String name, Float balance, String alias, String cvu, String userId) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.alias = alias;
        this.cvu = cvu;
        this.userId = userId;
    }

    public Account(String name, Float balance, String alias, String cvu, String userId) {
        this.name = name;
        this.balance = balance;
        this.alias = alias;
        this.cvu = cvu;
        this.userId = userId;
    }

    public Account() {
    }




}
