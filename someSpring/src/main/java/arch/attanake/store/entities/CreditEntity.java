package arch.attanake.store.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Credits")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long creditId;

    private BigDecimal startCreditAmount;

    @ManyToOne()
    @JoinColumn(name = "loanType", referencedColumnName = "loanTypeName")
    private LoanTypeEntity loanTypeEntity;

    private Integer loanTerm;

    @Version
    private Integer version;

    private BigDecimal totalAmount;

    private BigDecimal loanBalance;

    private LocalDateTime loanIssueDate;

    private LocalDateTime finalFeeDate;

    @OneToOne
    private CardAccountEntity cardAccount;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "owner", referencedColumnName = "clientId")
    private ClientEntity owner;
}
