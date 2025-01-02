package arch.attanake.store.repositories;

import arch.attanake.store.entities.CreditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<CreditEntity, Long> {
}
