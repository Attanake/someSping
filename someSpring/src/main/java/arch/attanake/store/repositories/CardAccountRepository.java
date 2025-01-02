package arch.attanake.store.repositories;

import arch.attanake.store.entities.CardAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardAccountRepository extends JpaRepository<CardAccountEntity, Long> {
}
