package arch.attanake.store.repositories;

import arch.attanake.store.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    Stream<TransactionEntity> streamAllByAccId(long accId);
}
