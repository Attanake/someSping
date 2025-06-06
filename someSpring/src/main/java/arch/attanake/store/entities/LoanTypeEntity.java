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
    @GeneratedValue
    private Long loanTypeId;

    @Column(unique = true)
    private String loanTypeName;

    private Float interestRate;

    private Integer maxLoanTerm;

    private Integer minLoanTerm;
}
