package arch.attanake.api.controllers;

import arch.attanake.api.dto.CardAccountTypeDto;
import arch.attanake.api.exceptions.BadRequestException;
import arch.attanake.api.factroies.CardAccountTypeDtoFactory;
import arch.attanake.store.entities.CardAccountTypeEntity;
import arch.attanake.store.repositories.CardAccountTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequiredArgsConstructor
public class CardAccountTypeController {

    private final CardAccountTypeRepository cardAccountTypeRepository;

    private static final String CREATE_CARD_ACCOUNT_TYPE = "/api/card_account_types";

    @PostMapping(CREATE_CARD_ACCOUNT_TYPE)
    public CardAccountTypeDto createAccountType(@RequestParam("acc_type") String accType,
                                                @RequestParam("interest_rate") Float interestRate,
                                                @RequestParam("calculation_interval") Integer calculationInterval,
                                                @RequestParam("max_acc_term") Integer maxAccTerm,
                                                @RequestParam("min_acc_term") Integer minAccTerm){

        cardAccountTypeRepository
                .findByAccType(accType)
                .ifPresent(acc ->{
                    throw new BadRequestException(accType + " is already exists");
                });

        CardAccountTypeEntity accountType = cardAccountTypeRepository.saveAndFlush(
                CardAccountTypeEntity.builder()
                        .accType(accType)
                        .interestRate(interestRate)
                        .calculationInterval(calculationInterval)
                        .minAccTerm(minAccTerm)
                        .maxAccTerm(maxAccTerm)
                        .build()
        );

         return CardAccountTypeDtoFactory.makeAccountTypeDtoFactory(accountType);
    }
}
