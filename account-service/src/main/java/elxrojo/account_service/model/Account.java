package elxrojo.account_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId")
    private Long id;

    @Column(name = "balance")
    private Float balance;

    @Column(name = "alias")
    private String alias;

    @Column(name = "cvu", length = 22, columnDefinition = "char(22)")
    private String cvu;
}