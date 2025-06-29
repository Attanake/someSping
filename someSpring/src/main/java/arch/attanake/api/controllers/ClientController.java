package arch.attanake.api.controllers;

import arch.attanake.api.dto.ClientDto;
import arch.attanake.api.exceptions.BadRequestException;
import arch.attanake.api.exceptions.NotFoundException;
import arch.attanake.api.factroies.ClientDtoFactory;
import arch.attanake.store.entities.ClientEntity;
import arch.attanake.store.repositories.ClientRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Tag(name = "client_controller")
@Transactional
@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;

    private static final String CREATE_USER = "/api/users";
    private static final String GET_USERS = "/api/users";
    private static final String GET_USERS_BY_ID = "/api/users/{client_id}";
    private static final String REGISTRATION = "/api/registration";
    private static final String HOME = "/api/home";

    @PostMapping(CREATE_USER)
    public ClientDto createUser(
            @RequestParam("identificationNum") String identificationNum,
            @RequestParam("name") String name,
            @RequestParam("secName") String secName,
            @RequestParam("surname") String surname,
            @RequestParam("birthdate") LocalDate birthdate,
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

    @GetMapping(GET_USERS_BY_ID)
    public ClientEntity getUser(@PathVariable("client_id") Long clientId){
        return clientRepository
                .findById(clientId)
                .orElseThrow(()-> new NotFoundException("Client(id =" + clientId + ") not found"));
    }

    @GetMapping(GET_USERS)
    public List<ClientDto> getUsers(
            @RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName) {

        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<ClientEntity> clientEntityStream = optionalPrefixName
                .map(clientRepository::streamAllByNameStartsWithIgnoringCase)
                .orElseGet(clientRepository::streamAllBy);

        return clientEntityStream.map(ClientDtoFactory::makeClientDto).collect(Collectors.toList());
    }

    @GetMapping(REGISTRATION)
    public String register(Model model, HttpSession session) {
        model.addAttribute("client", new ClientEntity());
        session.setAttribute("client", new ClientEntity());
        return "registration";
    }

    @PostMapping("/register")
    public String registerClient(@ModelAttribute ClientEntity client, HttpSession session, Model model,
                                 RedirectAttributes redirectAttributes) {
        ClientEntity newClient = clientRepository.saveAndFlush(
                ClientEntity.builder()
                        .identificationNum(client.getIdentificationNum())
                        .name(client.getName())
                        .secondName(client.getSecondName())
                        .surname(client.getSurname())
                        .birthDate(client.getBirthDate())
                        .secretWord(client.getSecretWord())
                        .email(client.getEmail())
                        .phoneNum(client.getPhoneNum())
                        .build()
        );
        session.setAttribute("client", newClient);
        model.addAttribute("client", newClient);
        redirectAttributes.addFlashAttribute("client", newClient);
        return "registrationPassword";
    }

    @GetMapping(HOME)
    public String home(HttpSession session, Model model){
        ClientEntity client = clientRepository.findById((Long) session.getAttribute("clientId"))
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));
        session.setAttribute("client", client);
        model.addAttribute("client", client);
        return "home";
    }
}
