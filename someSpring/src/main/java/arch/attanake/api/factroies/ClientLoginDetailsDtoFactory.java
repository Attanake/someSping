package arch.attanake.api.factroies;

import arch.attanake.api.dto.ClientLoginDetailsDto;
import arch.attanake.store.entities.ClientLoginDetailsEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientLoginDetailsDtoFactory {

    public static ClientLoginDetailsDto makeClientLoginDetailsDto(ClientLoginDetailsEntity entity){

        return ClientLoginDetailsDto.builder()
                .clientId(entity.getClientId())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .build();
    }
}
