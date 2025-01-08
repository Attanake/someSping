package arch.attanake.store.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "tr_id")
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "sender_acc_id", referencedColumnName = "accId")
    @JsonBackReference
    private CardAccountEntity senderAccId;

    @ManyToOne
    @JoinColumn(name = "payee_acc_id", referencedColumnName = "accId")
    @JsonBackReference
    private CardAccountEntity payeeAccId;

    @Column(name = "tr_amount")
    private BigDecimal transactionAmount;

    @Column(name = "tr_datetime")
    private LocalDateTime transactionDateTime = LocalDateTime.now();
}
