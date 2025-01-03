package arch.attanake.api.controllers;

import arch.attanake.api.dto.CreditDto;
import arch.attanake.api.exceptions.NotFoundException;
import arch.attanake.store.entities.ClientEntity;
import arch.attanake.store.entities.LoanType;
import arch.attanake.store.repositories.ClientRepository;
import arch.attanake.store.repositories.CreditRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Transactional
@RestController
@RequiredArgsConstructor
public class CreditController {

    private final CreditRepository creditRepository;
    private final ClientRepository clientRepository;

    public static final String CREATE_CREDIT = "/api/credits";

    public CreditDto createCredit(
            @RequestParam("loan_type") LoanType loanType,
            @RequestParam("start_credit_amount") BigDecimal startCreditAmount,
            @RequestParam("client_id") Long ClientId
            ){

        return null;
    }
}
