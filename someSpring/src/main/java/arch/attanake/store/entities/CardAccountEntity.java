package arch.attanake.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "Card_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long accId;


    @Enumerated(EnumType.STRING)
    private AccountType accType;

    private BigDecimal amountOnAcc;

    @Enumerated(EnumType.STRING)
    private Currencies accCurrency;

    private Float accInterestTerm;

    private LocalDateTime accTerm;

    private LocalDateTime calculationInterval;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "clientId")
    private ClientEntity owner;
}
