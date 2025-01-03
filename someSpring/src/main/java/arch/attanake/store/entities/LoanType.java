package arch.attanake.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "Credits")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanType {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long loanTypeId;

    private Float interestRate;

    private Instant maxLoanTerm;

    private Instant minLoanTerm;
}
