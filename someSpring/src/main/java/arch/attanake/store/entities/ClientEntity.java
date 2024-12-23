package arch.attanake.store.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import lombok.Setter;
import lombok.Getter;


import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "Clients")
@Setter
@Getter
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "clientid")
    private Long clientId;

    @Column(name = "secondname")
    private String secondName;

    private String name;

    private String surname;

    private Instant birthDate;

    private String secretWord;

    @Column(unique = true)
    private Long phoneNum;

    @Column(unique = true)
    private String email;

    @OneToMany
    private List<CardAccountEntity> accounts;

    @OneToMany
    private List<CreditEntity> credits;

    @OneToOne
    private ClientLoginDetailsEntity loginDetails;
}
