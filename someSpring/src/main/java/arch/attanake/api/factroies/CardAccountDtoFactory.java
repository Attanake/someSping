package arch.attanake.api.factroies;

import arch.attanake.api.dto.CardAccountDto;
import arch.attanake.store.entities.CardAccountEntity;
import org.springframework.stereotype.Component;

@Component
public class CardAccountDtoFactory {

    public static CardAccountDto makeCardAccountDtoFactory(CardAccountEntity entity){

        return CardAccountDto.builder()
                .accId(entity.getAccId())
                .accType(entity.getAccType())
                .amountOnAcc(entity.getAmountOnAcc())
                .accCurrency(entity.getAccCurrency())
                .accTerm(entity.getAccTerm())
                .build();
    }
}
