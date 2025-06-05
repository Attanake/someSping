package arch.attanake.api.controllers;

import arch.attanake.api.dto.ClientLoginDetailsDto;
import arch.attanake.api.exceptions.BadRequestException;
import arch.attanake.api.exceptions.NotFoundException;
import arch.attanake.api.factroies.ClientLoginDetailsDtoFactory;
import arch.attanake.store.entities.ClientEntity;
import arch.attanake.store.entities.ClientLoginDetailsEntity;
import arch.attanake.store.repositories.ClientLoginDetailsRepository;
import arch.attanake.store.repositories.ClientRepository;
import com.fasterxml.jackson.databind.DatabindContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "client_login_details_controller")
@Transactional
@Controller
@RequiredArgsConstructor
public class ClientLoginDetailsController {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);

    private final ClientLoginDetailsRepository clientLoginDetailsRepository;

    private final ClientRepository clientRepository;

    private static final String CREATE_USER_LOGIN_DETAILS = "/api/user_login_details/";
    private static final String GET_USER_LOGIN_DETAILS_BY_ID = "/api/get/user_login_details/{client_id}";
    private static final String AUTHORIZATION = "/api/authorization";
    private static final String REG_FORM = "/api/reg/user_login_details/{client_id}";


    @PostMapping(CREATE_USER_LOGIN_DETAILS)
    public String createLoginDetails(HttpSession session, Model model,
                                     @RequestParam("login") String login,
                                     @RequestParam("password") String password) {
        ClientEntity client = (ClientEntity) session.getAttribute("client");
        clientLoginDetailsRepository.saveAndFlush(
                ClientLoginDetailsEntity.builder()
                        .clientId(client.getClientId())
                        .password(encoder.encode(password))
                        .login(login)
                        .isAdmin(Boolean.FALSE)
                        .build()
        );

        return "home";
    }

    @GetMapping(GET_USER_LOGIN_DETAILS_BY_ID)
    public ClientLoginDetailsEntity getLoginDetails(@PathVariable("client_id") Long clientId){
        return clientLoginDetailsRepository
                .findById(clientId)
                .orElse(null);

    }

    @GetMapping(AUTHORIZATION)
    public String authorizationForm(Model model, HttpSession session) {
        return "authorization";
    }

    @GetMapping(REG_FORM)
    public String register(Model model, @PathVariable("client_id") Long clientId, HttpSession session){
        ClientEntity client = clientRepository
                .findById(clientId)
                .orElseThrow(() -> new BadRequestException("Client account(id=" + clientId + ") doesn't exist"));
        model.addAttribute("client", client);
        session.setAttribute("client", client);
        return "registration_password";
    }

    @PostMapping(AUTHORIZATION)
    public String checkDetails(HttpSession session,
                               Model model,
                               @RequestParam("login") String login,
                               @RequestParam("password") String password){
        Optional<ClientLoginDetailsEntity> clientDetailsOptional = clientLoginDetailsRepository.findByLogin(login);
        if (clientDetailsOptional.isPresent()) {
             ClientLoginDetailsEntity details = clientDetailsOptional.get();
            if (encoder.matches(password, details.getPassword())) {
                ClientEntity client = clientRepository.findById(details.getClientId()).orElse(null);
                model.addAttribute("client", client);
                return "home";
            }
        }
        return "authorization";
    }


}
