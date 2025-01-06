package arch.attanake.api.controllers;

import arch.attanake.api.dto.LoanTypeDto;
import arch.attanake.api.exceptions.BadRequestException;
import arch.attanake.api.factroies.LoanTypeDtoFactory;
import arch.attanake.store.entities.LoanTypeEntity;
import arch.attanake.store.repositories.LoanTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequiredArgsConstructor
public class LoanTypeController {

    private static LoanTypeRepository loanTypeRepository;

    private static final String CREATE_LOAN_TYPE = "/api/loan_types";

    public LoanTypeDto createLoanType(@RequestParam("loan_type") String loanTypeName,
                                         @RequestParam("interest_rate") Float interestRate,
                                         @RequestParam("max_loan_term") Integer maxLoanTerm,
                                         @RequestParam("min_loan_term") Integer minLoanTerm){

        loanTypeRepository
                .findByLoanType(loanTypeName)
                .ifPresent(loanType->{
                    throw new BadRequestException(loanTypeName + " is already exists");
                });

        LoanTypeEntity loanType = loanTypeRepository.saveAndFlush(
                LoanTypeEntity.builder()
                        .loanType(loanTypeName)
                        .interestRate(interestRate)
                        .maxLoanTerm(maxLoanTerm)
                        .minLoanTerm(minLoanTerm)
                        .build()
        );

        return LoanTypeDtoFactory.makeLoanTypeDto(loanType);
    }
}
