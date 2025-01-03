package arch.attanake.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "Credits")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long creditId;

    private BigDecimal startCreditAmount;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    private Instant loanTerm;

    private BigDecimal totalAmount;

    private BigDecimal loanBalance;

    private Instant loanIssueDate;

    private BigDecimal monthlyFee;

    private Instant finalFeeDate;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "clientId")
    private ClientEntity owner;
}
