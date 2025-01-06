package arch.attanake.api.factroies;

import arch.attanake.api.controllers.LoanTypeController;
import arch.attanake.api.dto.LoanTypeDto;
import arch.attanake.store.entities.LoanTypeEntity;

public class LoanTypeDtoFactory {

    public static LoanTypeDto makeLoanTypeDto(LoanTypeEntity entity){
        return LoanTypeDto.builder()
                .loanTypeId(entity.getLoanTypeId())
                .loanType(entity.getLoanType())
                .interestRate(entity.getInterestRate())
                .maxLoanTerm(entity.getMaxLoanTerm())
                .minLoanTerm(entity.getMinLoanTerm())
                .build();
    }
}
