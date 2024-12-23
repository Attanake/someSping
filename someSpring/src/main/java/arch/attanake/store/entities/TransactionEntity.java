package arch.attanake.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "Transactions")
@Getter
@Setter
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "trid")
    private Long transactionID;

    @ManyToOne
    private CardAccountEntity senderAccId;

    @ManyToOne
    private CardAccountEntity payeeAccId;

    @Column(name = "tramount")
    private BigDecimal transactionAmount;

    @Column(name = "trdatetime")
    private Instant transactionDateTIme = Instant.now();
}
