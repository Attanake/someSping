package arch.attanake.store.repositories;

import arch.attanake.store.entities.ClientLoginDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLoginDetailsRepository extends JpaRepository<ClientLoginDetailsEntity, Long> {
}
