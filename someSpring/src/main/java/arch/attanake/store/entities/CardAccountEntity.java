package arch.attanake.store.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "Card_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardAccountEntity {

    @Id
    @GeneratedValue
    private Long accId;

    @ManyToOne
    @JoinColumn(name = "accountType", referencedColumnName = "accType")
    private CardAccountTypeEntity accType;

    private BigDecimal amountOnAcc;

    @Enumerated(EnumType.STRING)
    private Currencies accCurrency;

    private Integer accTerm;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "owner", referencedColumnName = "clientId")
    private ClientEntity owner;
}
