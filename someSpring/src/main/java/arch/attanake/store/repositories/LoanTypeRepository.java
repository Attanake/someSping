package arch.attanake.store.repositories;

import arch.attanake.store.entities.LoanTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanTypeRepository extends JpaRepository<LoanTypeEntity, Long> {

    Optional<LoanTypeEntity> findByLoanType(String loanType);
}

