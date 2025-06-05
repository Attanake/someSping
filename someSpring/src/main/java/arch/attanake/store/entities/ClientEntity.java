package arch.attanake.store.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "Clients")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long clientId;

    @Column(unique = true)
    private String identificationNum;

    private String name;

    private String secondName;

    private String surname;

    private LocalDate birthDate;

    private String secretWord;

    @Column(unique = true)
    private Long phoneNum;

    @Column(unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "clientId")
    private ClientLoginDetailsEntity loginDetails;

    @OneToMany
    @JoinColumn(name = "credits")
    private List<CreditEntity> credits;

    @OneToMany
    @JoinColumn(name = "card_accounts")
    private List<CardAccountEntity> cardAccounts;

    @OneToMany
    @JoinColumn(name = "transactions")
    private List<TransactionEntity> transactions;
}
