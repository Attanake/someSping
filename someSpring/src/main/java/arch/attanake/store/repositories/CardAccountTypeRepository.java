package arch.attanake.store.repositories;

import arch.attanake.store.entities.CardAccountTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardAccountTypeRepository extends JpaRepository<CardAccountTypeEntity, Long> {

    Optional<CardAccountTypeEntity> findByAccType(String accType);

    List<CardAccountTypeEntity> findAll();
}
