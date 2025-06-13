package arch.attanake.api.controllers;

import arch.attanake.api.exceptions.BadRequestException;
import arch.attanake.store.entities.ClientEntity;
import arch.attanake.store.entities.ClientLoginDetailsEntity;
import arch.attanake.store.repositories.ClientLoginDetailsRepository;
import arch.attanake.store.repositories.ClientRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private static final String REG_FORM = "/api/reg/user_login_details/";


    @PostMapping(CREATE_USER_LOGIN_DETAILS)
    public String createLoginDetails(HttpSession session, Model model,
                                     @RequestParam("login") String login,
                                     @RequestParam("password") String password,
                                     RedirectAttributes redirectAttributes) {
        ClientEntity client = (ClientEntity) session.getAttribute("client");
        clientLoginDetailsRepository.saveAndFlush(
                ClientLoginDetailsEntity.builder()
                        .clientId(client.getClientId())
                        .password(encoder.encode(password))
                        .login(login)
                        .isAdmin(Boolean.FALSE)
                        .build()
        );
        session.setAttribute("client", client);
        model.addAttribute("client", client);
        redirectAttributes.addFlashAttribute("client", client);
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
        session.setAttribute("loginDetails", new ClientLoginDetailsEntity());
        session.setAttribute("client", new ClientEntity());
        return "authorization";
    }

    @GetMapping(REG_FORM)
    public String register(Model model, HttpSession session){
        ClientEntity client = (ClientEntity) session.getAttribute("client");
        clientRepository
                .findById(client.getClientId())
                .orElseThrow(() -> new BadRequestException("Client account(id=" + client.getClientId() + ") doesn't exist"));
        model.addAttribute("client", client);
        session.setAttribute("client", client);
        return "registrationPassword";
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
                session.setAttribute("client", client);
                return "home";
            }
        }
        return "authorization";
    }


}
