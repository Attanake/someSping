package arch.attanake.api.controllers;

import arch.attanake.api.dto.CreditDto;
import arch.attanake.api.exceptions.NotFoundException;
import arch.attanake.api.factroies.CardAccountDtoFactory;
import arch.attanake.api.factroies.CreditDtoFactory;
import arch.attanake.store.entities.*;
import arch.attanake.store.repositories.CardAccountRepository;
import arch.attanake.store.repositories.ClientRepository;
import arch.attanake.store.repositories.CreditRepository;
import arch.attanake.store.repositories.LoanTypeRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Tag(name = "credit_controller")
@Transactional
@RestController
@RequiredArgsConstructor
public class CreditController {

    public static double pow(Double value, int powValue) {
        if (powValue == 1) {
            return value;
        } else {
            return value * pow(value, powValue - 1);
        }
    }


    private final CreditRepository creditRepository;
    private final ClientRepository clientRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final CardAccountRepository cardAccountRepository;

    private static final String CREATE_CREDIT = "/api/credits";
    private static final String GET_MONTHLY_PAYMENTS = "/api/credits/{credit_id}/monthly_payment";

    @PostMapping(CREATE_CREDIT)
    public CreditDto createCredit(
            @RequestParam("loan_type") String loanTypeName,
            @RequestParam("start_credit_amount") BigDecimal startCreditAmount,
            @RequestParam("loan_term") Integer loanDuration,
            @RequestParam("client_id") Long clientId
            ){

        LoanTypeEntity loanType = loanTypeRepository
                .findByLoanType(loanTypeName)
                .orElseThrow(()-> new NotFoundException("Loan type " + loanTypeName +" doesn't exists"));

        ClientEntity client = clientRepository
                .findById(clientId)
                .orElseThrow(()-> new NotFoundException("Client (id=" + clientId + ") doesn't exists"));

        CardAccountEntity cardAccount = client.getCardAccounts().getFirst();
        cardAccount.setAmountOnAcc(cardAccount.getAmountOnAcc().add(startCreditAmount));
        CardAccountDtoFactory.makeCardAccountDtoFactory(cardAccount);

        CardAccountEntity newAccount = cardAccountRepository.saveAndFlush(
                CardAccountEntity.builder()
                        .amountOnAcc(BigDecimal.valueOf(0L))
                        .build()
        );

        CreditEntity credit = creditRepository.saveAndFlush(
                CreditEntity.builder()
                        .creditId(newAccount.getAccId())
                        .startCreditAmount(startCreditAmount)
                        .loanTypeEntity(loanType)
                        .loanTerm(loanDuration)
                        .totalAmount(sumOfPayments(startCreditAmount, loanDuration, loanType.getInterestRate()))
                        .loanBalance(sumOfPayments(startCreditAmount, loanDuration, loanType.getInterestRate()))
                        .loanIssueDate(LocalDateTime.now())
                        .finalFeeDate(LocalDateTime.now().plus(loanDuration, ChronoUnit.MONTHS))
                        .owner(client)
                        .build()
        );

        client.getCredits().add(credit);

        return CreditDtoFactory.makeCreditDto(credit);
    }


    @GetMapping(GET_MONTHLY_PAYMENTS)
    public Map<LocalDate,BigDecimal> getMonthlyPayment(@PathVariable("credit_id") Long creditId){

        CreditEntity credit = creditRepository
                .findById(creditId)
                .orElseThrow(()-> new NotFoundException("Credit (id=" + creditId + ") doesn't exists"));

        Float interestRate = credit.getLoanTypeEntity().getInterestRate();
        Integer loanTerm = credit.getLoanTerm();


        BigDecimal monthlyPaymentAmount = credit.getStartCreditAmount()
                .multiply(BigDecimal.valueOf(credit.getLoanTypeEntity().getInterestRate()/12))
                .multiply(BigDecimal.valueOf(pow((double) (1 + interestRate / 12), loanTerm)))
                .divide(BigDecimal.valueOf(pow((double) (1 + interestRate / 12), loanTerm)-1), 2, RoundingMode.HALF_EVEN);

        Map<LocalDate, BigDecimal> monthlyPayment = new HashMap<>();

        LocalDate date = LocalDate.now();
        for(int i = 0; i < credit.getLoanTerm(); i++) {
            date = date.plusMonths(1);
            monthlyPayment.put(date, monthlyPaymentAmount);
        }
        return monthlyPayment;
    }

    public BigDecimal sumOfPayments(BigDecimal startAmount, Integer loanTerm, Float interestRate){

        BigDecimal monthlyPayment = startAmount
                .multiply(BigDecimal.valueOf(interestRate/12))
                .multiply(BigDecimal.valueOf(pow((double) (1 + interestRate / 12), loanTerm)))
                .divide(BigDecimal.valueOf(pow((double) (1 + interestRate / 12), loanTerm)-1), 2, RoundingMode.HALF_EVEN);

        return monthlyPayment.multiply(BigDecimal.valueOf(loanTerm));
    }
}
