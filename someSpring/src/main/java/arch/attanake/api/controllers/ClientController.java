package arch.attanake.api.controllers;

import arch.attanake.api.dto.ClientDto;
import arch.attanake.api.exceptions.BadRequestException;
import arch.attanake.api.factroies.ClientDtoFactory;

import arch.attanake.store.entities.ClientEntity;
import arch.attanake.store.repositories.ClientLoginDetailsRepository;
import arch.attanake.store.repositories.ClientRepository;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Transactional
@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;

    private final ClientDtoFactory clientDtoFactory;

    private static final String CREATE_USER = "/api/users";

    @PostMapping(CREATE_USER)
    public ClientDto createUser(
            @RequestParam("identificationNum") String identificationNum,
            @RequestParam("name") String name,
            @RequestParam("secName") String secName,
            @RequestParam("surname") String surname,
            @RequestParam("birthdate") Instant birthdate,
            @RequestParam("secretWord") String secretWord,
            @RequestParam("email") String email,
            @RequestParam("phoneNum") Long phoneNum){

        clientRepository
                .findByIdentificationNum(identificationNum)
                .ifPresent(client->{
                    throw new BadRequestException(
                            String.format("Client with \"%s\" identification number already exists",identificationNum));
                });

        ClientEntity client = clientRepository.saveAndFlush(
                ClientEntity.builder()
                        .identificationNum(identificationNum)
                        .name(name)
                        .secondName(secName)
                        .surname(surname)
                        .birthDate(birthdate)
                        .secretWord(secretWord)
                        .email(email)
                        .phoneNum(phoneNum)
                        .build()
        );

    return ClientDtoFactory.makeClientDto(client);
    }
}
