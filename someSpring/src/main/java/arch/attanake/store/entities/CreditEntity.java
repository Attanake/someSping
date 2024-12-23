package arch.attanake.store.entities;

import arch.attanake.store.LoanType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "Credits")
@Getter
@Setter
public class CreditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long creditId;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    private float interestRate;

    private Instant loanTerm;

    private BigDecimal totalAmount;

    private BigDecimal loanBalance;

    private Instant loanIssueDate;

    private BigDecimal monthlyFee;

    private Instant finalFeeDate;

}
