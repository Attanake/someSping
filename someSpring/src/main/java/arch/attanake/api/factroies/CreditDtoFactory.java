package arch.attanake.api.factroies;

import arch.attanake.api.dto.CreditDto;
import arch.attanake.store.entities.CreditEntity;
import org.springframework.stereotype.Component;

@Component
public class CreditDtoFactory {

    public static CreditDto makeCreditDto(CreditEntity entity){

        return CreditDto.builder()
                .creditId(entity.getCreditId())
                .loanTypeEntity(entity.getLoanTypeEntity())
                .totalAmount(entity.getTotalAmount())
                .loanBalance(entity.getLoanBalance())
                .loanIssueDate(entity.getLoanIssueDate())
                .finalFeeDate(entity.getFinalFeeDate())
                .build();
    }
}
