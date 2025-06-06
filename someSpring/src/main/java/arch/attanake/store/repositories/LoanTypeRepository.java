package arch.attanake.store.repositories;

import arch.attanake.store.entities.LoanTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

public interface LoanTypeRepository extends JpaRepository<LoanTypeEntity, Long> {

    Optional<LoanTypeEntity> findByLoanTypeName(String loanType);

    List<LoanTypeEntity> findAll();
}

