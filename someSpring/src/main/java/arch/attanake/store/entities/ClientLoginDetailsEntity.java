package arch.attanake.store.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Client_login_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientLoginDetailsEntity {

    @Id
    private Long clientId;

    @Column(unique = true)
    private String login;

    private String password;

    @OneToOne
    @JoinColumn(name = "owner", referencedColumnName = "clientId")
    private ClientEntity client;
}
