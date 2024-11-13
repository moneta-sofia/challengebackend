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

    @Column(name = "amount", nullable = false)
    private Float amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "activityType")
    private ActivityType activityType; // transfer attribute

    @Enumerated(EnumType.STRING)
    @Column(name = "transactionType" , nullable = false)
    private TransactionType transactionType;

    @Column(name = "origin", length = 22)
    private String origin;  // transfer attribute

    @Column(name = "name")
    private String name; // transfer attribute

    @Column(name = "destination")
    private String destination; // transfer attribute

    @Column(name = "dated", nullable = false)
    private LocalDateTime dated;

    @Column(name = "accountId")
    private Long accountId;

    public Transaction() {
    }

    // deposit

    public Transaction(Long id, Float amount, TransactionType transactionType, LocalDateTime dated, Long accountId) {
        this.id = id;
        this.amount = amount;
        this.transactionType = transactionType;
        this.dated = dated;
        this.accountId = accountId;
    }
    public Transaction(Float amount, TransactionType transactionType, LocalDateTime dated, Long accountId) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.dated = dated;
        this.accountId = accountId;
    }




//    transfer


    public Transaction(Long id, Float amount, ActivityType activityType, TransactionType transactionType, String origin, String name, String destination, LocalDateTime dated, Long accountId) {
        this.id = id;
        this.amount = amount;
        this.activityType = activityType;
        this.transactionType = transactionType;
        this.origin = origin;
        this.name = name;
        this.destination = destination;
        this.dated = dated;
        this.accountId = accountId;
    }

    public Transaction(Float amount, ActivityType activityType, TransactionType transactionType, String origin, String name, String destination, LocalDateTime dated, Long accountId) {
        this.amount = amount;
        this.activityType = activityType;
        this.transactionType = transactionType;
        this.origin = origin;
        this.name = name;
        this.destination = destination;
        this.dated = dated;
        this.accountId = accountId;
    }
}

