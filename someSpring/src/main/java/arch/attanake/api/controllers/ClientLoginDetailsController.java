package arch.attanake.api.controllers;

import arch.attanake.store.repositories.ClientLoginDetailsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequiredArgsConstructor
public class ClientLoginDetailsController {

    private final ClientLoginDetailsRepository clientLoginDetailsRepository;

    private static final String CREATE_USER = "/api/users";


}
