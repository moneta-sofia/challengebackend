package elxrojo.transaction_service.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "transactionType")
    private TransactionType transactionType;

    @Column(name = "origin", length = 22)
    private String origin;

    @Column(name = "name")
    private String name;

    @Column(name = "destination")
    private String destination;

    @Column(name = "dated")
    private LocalDateTime dated;

    @Column(name = "accountId")
    private Long accountId;

    public Transaction() {
    }

    public Transaction(Float amount, TransactionType transactionType, String origin, String name, String destination, LocalDateTime dated, Long accountId) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.origin = origin;
        this.name = name;
        this.destination = destination;
        this.dated = dated;
        this.accountId = accountId;
    }

    public Transaction(Long id, Float amount, TransactionType transactionType, String origin, String name, String destination, LocalDateTime dated, Long accountId) {
        this.id = id;
        this.amount = amount;
        this.transactionType = transactionType;
        this.origin = origin;
        this.name = name;
        this.destination = destination;
        this.dated = dated;
        this.accountId = accountId;
    }
}
