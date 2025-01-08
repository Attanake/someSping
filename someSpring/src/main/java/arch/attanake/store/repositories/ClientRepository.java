package arch.attanake.store.repositories;

import arch.attanake.store.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findByIdentificationNum(String identificationNum);

    Optional<ClientEntity> findById(Long clientId);

    Stream<ClientEntity> streamAllBy();

    Stream<ClientEntity> streamAllByNameStartsWithIgnoringCase(String prefixName);

}
