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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;

@Tag(name = "card_account_controller")
@Transactional
@RestController
@RequiredArgsConstructor
public class CardAccountController {

    private final CardAccountRepository cardAccountRepository;

    private final ClientRepository clientRepository;

    private final CardAccountTypeRepository cardAccountTypeRepository;

    private static final String CREATE_CARD_ACCOUNT = "/api/card_accounts";

    @PostMapping(CREATE_CARD_ACCOUNT)
    public CardAccountDto createCardAccount(@RequestParam("accType") String accountType,
                                            @RequestParam("amount_on_acc") BigDecimal amountOnAcc,
                                            @RequestParam("acc_currency") Currencies currency,
                                            @RequestParam("acc_term") Integer accTerm,
                                            @RequestParam("client_id") Long clientId ){

        CardAccountTypeEntity accTypeEntity = cardAccountTypeRepository
                .findByAccType(accountType)
                .orElseThrow(()-> new NotFoundException("Account type \"" + accountType + "\" doesn't exists"));

        ClientEntity clientEntity = clientRepository
                .findById(clientId)
                .orElseThrow(()-> new NotFoundException("Client(id=" + clientId + ") cannot be found"));

        if(accTerm >= accTypeEntity.getMaxAccTerm() || accTerm <= accTypeEntity.getMinAccTerm())
            throw new BadRequestException("Account term is out of bounds");

        CardAccountEntity cardAccount = cardAccountRepository.saveAndFlush(
                CardAccountEntity.builder()
                        .accType(accTypeEntity)
                        .amountOnAcc(amountOnAcc)
                        .accCurrency(currency)
                        .accTerm(accTerm)
                        .owner(clientEntity)
                        .build()
        );

        clientEntity.getCardAccounts().add(cardAccount);

        return CardAccountDtoFactory.makeCardAccountDtoFactory(cardAccount);
    }
}
