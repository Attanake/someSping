package arch.attanake.store.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "Clients")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long clientId;

    private String identificationNum;

    private String name;

    private String secondName;

    private String surname;

    private Instant birthDate;

    private String secretWord;

    @Column(unique = true)
    private Long phoneNum;

    @Column(unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "clientId")
    private ClientLoginDetailsEntity loginDetails;
}
