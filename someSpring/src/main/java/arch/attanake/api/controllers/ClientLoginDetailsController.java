package arch.attanake.api.controllers;

import arch.attanake.api.dto.ClientLoginDetailsDto;
import arch.attanake.api.exceptions.BadRequestException;
import arch.attanake.api.factroies.ClientLoginDetailsDtoFactory;
import arch.attanake.store.entities.ClientEntity;
import arch.attanake.store.entities.ClientLoginDetailsEntity;
import arch.attanake.store.repositories.ClientLoginDetailsRepository;
import arch.attanake.store.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.jar.Attributes;


@Transactional
@RestController
@RequiredArgsConstructor
public class ClientLoginDetailsController {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);

    private final ClientLoginDetailsRepository clientLoginDetailsRepository;

    private final ClientRepository clientRepository;

    private static final String CREATE_USER_LOGIN_DETAILS = "/api/user_login_details/{client_id}";

    @PostMapping(CREATE_USER_LOGIN_DETAILS)
    public ClientLoginDetailsDto createLoginDetails(
            @PathVariable("client_id") Long clientId,
            @RequestParam("password") String password,
            @RequestParam("login") String login){

        clientLoginDetailsRepository
                .findById(clientId)
                .ifPresent(details->{
                    throw new BadRequestException("Client(id = " + clientId + ") already have a login details");
                });

        ClientLoginDetailsEntity clientLoginDetails = clientLoginDetailsRepository.saveAndFlush(
                ClientLoginDetailsEntity.builder()
                        .clientId(clientId)
                        .password("{bcrypt}" + encoder.encode(password))
                        .login(login)
                        .isAdmin(Boolean.FALSE)
                        .build()
        );

        return ClientLoginDetailsDtoFactory.makeClientLoginDetailsDto(clientLoginDetails);
    }

    @GetMapping("api/user_login_details/{client_id}")
    public ClientLoginDetailsEntity getLoginDetails(@PathVariable("client_id") Long clientId){
        return clientLoginDetailsRepository
                .findById(clientId)
                .orElse(null);

    }
}
