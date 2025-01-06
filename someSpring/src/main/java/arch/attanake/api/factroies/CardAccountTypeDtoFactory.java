package arch.attanake.api.factroies;

import arch.attanake.api.dto.CardAccountTypeDto;
import arch.attanake.store.entities.CardAccountTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class CardAccountTypeDtoFactory {

    public static CardAccountTypeDto makeAccountTypeDtoFactory(CardAccountTypeEntity entity){

        return CardAccountTypeDto.builder()
                .accTypeId(entity.getAccTypeId())
                .accType(entity.getAccType())
                .interestRate(entity.getInterestRate())
                .calculationInterval(entity.getCalculationInterval())
                .maxAccTerm(entity.getMaxAccTerm())
                .minAccTerm(entity.getMinAccTerm())
                .build();
    }
}
