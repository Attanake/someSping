package arch.attanake.api.factroies;

import arch.attanake.api.dto.ClientDto;
import arch.attanake.store.entities.ClientEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientDtoFactory {

    public static ClientDto makeClientDto(ClientEntity entity){

        return ClientDto.builder()
                .identificationNum(entity.getIdentificationNum())
                .name(entity.getName())
                .secondName(entity.getSecondName())
                .surname(entity.getSurname())
                .birthDate(entity.getBirthDate())
                .secretWord(entity.getSecretWord())
                .phoneNum(entity.getPhoneNum())
                .email(entity.getEmail())
                .transactions(entity.getTransactions())
                .cardAccounts(entity.getCardAccounts())
                .credits(entity.getCredits())
                .build();
    }
}
