package arch.attanake.api.controllers;

import arch.attanake.api.dto.TransactionDto;
import arch.attanake.api.exceptions.BadRequestException;
import arch.attanake.api.exceptions.InsufficientBalanceException;
import arch.attanake.api.factroies.CardAccountDtoFactory;
import arch.attanake.api.factroies.TransactionDtoFactory;
import arch.attanake.store.entities.CardAccountEntity;
import arch.attanake.store.entities.ClientEntity;
import arch.attanake.store.entities.TransactionEntity;
import arch.attanake.store.repositories.CardAccountRepository;
import arch.attanake.store.repositories.TransactionRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Tag(name = "transactions_controller")
@Transactional
@Controller
@RequiredArgsConstructor
public class TransactionController {

    private final CardAccountRepository cardAccountRepository;
    private final TransactionRepository transactionRepository;

    private static final String CREATE_TRANSACTION = "/api//transactions/new";
    private static final String FIND_ALL_TRANSACTIONS = "/api/{acc_id}/transactions/find_all";

    @PostMapping(CREATE_TRANSACTION)
    @Transactional
    public HttpStatus createTransaction(@RequestParam("payeeAccId") Long payeeAccId,
                                        @RequestParam("senderAccId") Long senderAccId,
                                        @RequestParam("transaction_amount") BigDecimal transactionAmount,
                                        HttpSession session) {

        if(transactionAmount.intValue()<=2){
            throw new BadRequestException("Transaction amount is less than required");
        }

        CardAccountEntity senderAcc = cardAccountRepository
                .findByAccId(senderAccId)
                .orElseThrow(()-> new BadRequestException("Sender account(id="+  senderAccId + ") doesn't exists"));


        CardAccountEntity payeeAcc = cardAccountRepository
                .findByAccId(payeeAccId)
                .orElseThrow(()-> new BadRequestException("Payee account(id="+  payeeAccId + ") doesn't exists"));

        ClientEntity sender = senderAcc.getOwner();


        ClientEntity payee = payeeAcc.getOwner();


        if(senderAcc.getAmountOnAcc().compareTo(transactionAmount) < 0){
            throw new InsufficientBalanceException("Insufficient funds on balance");
        }

        senderAcc = cardAccountRepository.saveAndFlush(
                CardAccountEntity.builder()
                        .accId(senderAccId)
                        .amountOnAcc(senderAcc.getAmountOnAcc().subtract(transactionAmount))
                        .build()
        );

        CardAccountDtoFactory.makeCardAccountDtoFactory(senderAcc);

        payeeAcc = cardAccountRepository.saveAndFlush(
                CardAccountEntity.builder()
                        .accId(payeeAccId)
                        .amountOnAcc(payeeAcc.getAmountOnAcc().add(transactionAmount))
                        .build()
        );

        CardAccountDtoFactory.makeCardAccountDtoFactory(payeeAcc);

        TransactionEntity transactionEntity = transactionRepository.saveAndFlush(
                TransactionEntity.builder()
                        .senderAccId(senderAcc)
                        .payeeAccId(payeeAcc)
                        .transactionAmount(transactionAmount)
                        .transactionDateTime(LocalDateTime.now())
                        .build()
        );

        sender.getTransactions().add(transactionEntity);
        payee.getTransactions().add(transactionEntity);

        return HttpStatus.OK;
    }

    @GetMapping(FIND_ALL_TRANSACTIONS)
    public List<TransactionDto> findAllTransactions(@PathVariable("acc_id")  Long accId){

        Stream<TransactionEntity> transactionEntityStream = transactionRepository.streamAllByTransactionId(accId);

        return transactionEntityStream.map(TransactionDtoFactory::makeTransactionDto).collect(Collectors.toList());
    }

    @GetMapping("/api/transactionFrom")
    public String showTransactionForm(HttpSession session, Model model) {
        ClientEntity client = (ClientEntity) session.getAttribute("client");
        model.addAttribute("userAccounts", client.getCardAccounts() );
        return "transaction";
    }
}
