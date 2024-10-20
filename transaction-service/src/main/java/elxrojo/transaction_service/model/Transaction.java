package elxrojo.transaction_service.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionId")
    private Long id;

    @Column(name = "transactionType")
    private TransactionType transactionType;

    @Column(name = "origin", length = 22)
    private String origin;

    @Column(name = "name")
    private String name;

    @Column(name = "destination")
    private String destination;

    @Column(name = "dated")
    private Date dated;

    public Transaction() {
    }

    public Transaction(Long id, TransactionType transactionType, String origin, String name, String destination, Date dated) {
        this.id = id;
        this.transactionType = transactionType;
        this.origin = origin;
        this.name = name;
        this.destination = destination;
        this.dated = dated;
    }

    public Transaction(TransactionType transactionType, String origin, String name, String destination, Date dated) {
        this.transactionType = transactionType;
        this.origin = origin;
        this.name = name;
        this.destination = destination;
        this.dated = dated;
    }
}


enum TransactionType {
    transfer,
    deposit
}
