package arch.attanake.store.repositories;

import arch.attanake.store.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findByIdentificationNum(String identificationNum);

}
