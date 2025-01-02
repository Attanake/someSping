package arch.attanake.api.factroies;

import arch.attanake.api.dto.CreditDto;
import arch.attanake.store.entities.CreditEntity;
import org.springframework.stereotype.Component;

@Component
public class CreditDtoFactory {

    public CreditDto makeCreditDto(CreditEntity entity){

        return CreditDto.builder()
                .creditId(entity.getCreditId())
                .loanType(entity.getLoanType())
                .interestRate(entity.getInterestRate())
                .loanTerm(entity.getLoanTerm())
                .totalAmount(entity.getTotalAmount())
                .loanBalance(entity.getLoanBalance())
                .loanIssueDate(entity.getLoanIssueDate())
                .monthlyFee(entity.getMonthlyFee())
                .finalFeeDate(entity.getFinalFeeDate())
                .build();
    }
}
