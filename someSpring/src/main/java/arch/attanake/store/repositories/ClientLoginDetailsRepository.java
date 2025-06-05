package arch.attanake.store.repositories;

import arch.attanake.store.entities.ClientLoginDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientLoginDetailsRepository extends JpaRepository<ClientLoginDetailsEntity, Long> {

    Optional<ClientLoginDetailsEntity> findById(Long clientId);
    Optional<ClientLoginDetailsEntity> findByLogin(String login);

    boolean existsByLogin(String login);
}
