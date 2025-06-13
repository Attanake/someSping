package arch.attanake.api.controllers;

import arch.attanake.api.dto.CardAccountDto;
import arch.attanake.api.exceptions.BadRequestException;
import arch.attanake.api.exceptions.NotFoundException;
import arch.attanake.api.factroies.CardAccountDtoFactory;
import arch.attanake.store.entities.CardAccountEntity;
import arch.attanake.store.entities.CardAccountTypeEntity;
import arch.attanake.store.entities.ClientEntity;
import arch.attanake.store.entities.Currencies;
import arch.attanake.store.repositories.CardAccountRepository;
import arch.attanake.store.repositories.CardAccountTypeRepository;
import arch.attanake.store.repositories.ClientRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.html.HTMLTableCaptionElement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "card_account_controller")
@Transactional
@Controller
@RequiredArgsConstructor
public class CardAccountController {

    private final CardAccountRepository cardAccountRepository;

    private final ClientRepository clientRepository;

    private final CardAccountTypeRepository cardAccountTypeRepository;

    private static final String CREATE_CARD_ACCOUNT = "/api/card_accounts";

    private final String CREATE_CARD_ACCOUNT_FORM = "/api/card_accounts/create";

    @PostMapping(CREATE_CARD_ACCOUNT)
    public String createCardAccount(@ModelAttribute("cardAccount") CardAccountEntity cardAccount,
                                            @ModelAttribute("accType") CardAccountTypeEntity accType,
                                            HttpSession session, Model model) {


        CardAccountTypeEntity accountType = cardAccountTypeRepository.findByAccType(accType.getAccType().replaceAll("^,", ""))
                .orElseThrow(() -> new IllegalArgumentException("Invalid account type"));

        ClientEntity client = (ClientEntity) session.getAttribute("client");


        CardAccountEntity newCardAccount = cardAccountRepository.saveAndFlush(
                CardAccountEntity.builder()
                        .accType(accountType)
                        .amountOnAcc(BigDecimal.ZERO)
                        .accCurrency(cardAccount.getAccCurrency())
                        .accTerm(cardAccount.getAccTerm())
                        .owner(client)
                        .build()
        );

        if(client.getCardAccounts() == null){
            client.setCardAccounts(new ArrayList<>());
        }

        client.getCardAccounts().add(newCardAccount);

        clientRepository.save(client);

        session.setAttribute("client", client);
        model.addAttribute("client", client);
       return "home";
    }

    @GetMapping(CREATE_CARD_ACCOUNT_FORM)
    public String createCardAccountForm(HttpSession session, Model model){
        model.addAttribute("client",(ClientEntity) session.getAttribute("client"));
        model.addAttribute("cardAccount", new CardAccountEntity());
        model.addAttribute("accountTypes", cardAccountTypeRepository.findAll());
        return "CreateCardAccountForm";
    }
}
