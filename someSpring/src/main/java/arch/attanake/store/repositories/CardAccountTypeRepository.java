package arch.attanake.store.repositories;

import arch.attanake.store.entities.CardAccountTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardAccountTypeRepository extends JpaRepository<CardAccountTypeEntity, Long> {

    Optional<CardAccountTypeEntity> findByAccTypeId(Long accId);

    Optional<CardAccountTypeEntity> findByAccType(String accType);
}
