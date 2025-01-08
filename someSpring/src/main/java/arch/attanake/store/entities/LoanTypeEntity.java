package arch.attanake.store.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Loan_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long loanTypeId;

    @Column(unique = true)
    private String loanType;

    private Float interestRate;

    private Integer maxLoanTerm;

    private Integer minLoanTerm;
}
