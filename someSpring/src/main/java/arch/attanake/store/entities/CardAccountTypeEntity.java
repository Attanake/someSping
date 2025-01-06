package arch.attanake.store.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Account_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardAccountTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long accTypeId;

    @Column(unique = true)
    private String accType;

    private Float interestRate;

    private Integer calculationInterval;

    private Integer maxAccTerm;

    private Integer minAccTerm;
}
