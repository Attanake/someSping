package arch.attanake.store.entities;

import arch.attanake.store.AccountType;
import arch.attanake.store.Currencies;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "Card_account")
@Getter
@Setter
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

    private Instant accTerm;

    private Instant calculationInterval;
}
