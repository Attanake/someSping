package arch.attanake.store.repositories;

import arch.attanake.store.entities.CardAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardAccountRepository extends JpaRepository<CardAccountEntity, Long> {

    Optional<CardAccountEntity> findByAccId(Long accId);
}
