package arch.attanake.api.factroies;

import arch.attanake.api.dto.CardAccountDto;
import arch.attanake.api.dto.ClientDto;
import arch.attanake.store.entities.CardAccountEntity;
import arch.attanake.store.entities.ClientEntity;
import org.springframework.stereotype.Component;

@Component
public class CardAccountDtoFactory {

    public CardAccountDto makeCardAccountDtoFactory(CardAccountEntity entity){

        return CardAccountDto.builder()
                .accId(entity.getAccId())
                .accType(entity.getAccType())
                .amountOnAcc(entity.getAmountOnAcc())
                .accCurrency(entity.getAccCurrency())
                .accInterestTerm(entity.getAccInterestTerm())
                .accTerm(entity.getAccTerm())
                .calculationInterval(entity.getCalculationInterval())
                .build();
    }
}
