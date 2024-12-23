package arch.attanake.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Client_login_details")
@Getter
@Setter
public class ClientLoginDetailsEntity {

    @Id
    private Long clientId;

    @Column(unique = true)
    private String login;

    private String password;
}
