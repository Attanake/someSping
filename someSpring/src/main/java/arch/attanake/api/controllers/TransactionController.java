package arch.attanake.api.controllers;

import arch.attanake.api.dto.TransactionDto;
import arch.attanake.api.exceptions.BadRequestException;
import arch.attanake.api.exceptions.InsufficientBalanceException;
import arch.attanake.api.factroies.TransactionDtoFactory;
import arch.attanake.store.entities.CardAccountEntity;
import arch.attanake.store.entities.TransactionEntity;
import arch.attanake.store.repositories.CardAccountRepository;
import arch.attanake.store.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Transactional
@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final CardAccountRepository cardAccountRepository;
    private final TransactionRepository transactionRepository;

    private static final String CREATE_TRANSACTION = "/api/transactions";

    @PostMapping(CREATE_TRANSACTION)
    public TransactionDto createTransaction(@RequestParam("sender_acc_id") Long senderAccId,
                                            @RequestParam("payee_acc_id") Long payeeAccId,
                                            @RequestParam("transaction_amount") BigDecimal transactionAmount){

        CardAccountEntity sender = cardAccountRepository
                .findByAccId(senderAccId)
                .orElseThrow(()-> new BadRequestException("Sender account(id="+  senderAccId + ") doesn't exists"));

        CardAccountEntity payee = cardAccountRepository
                .findByAccId(payeeAccId)
                .orElseThrow(()-> new BadRequestException("Payee account(id="+  payeeAccId + ") doesn't exists"));

        if(sender.getAmountOnAcc().compareTo(transactionAmount) < 0){
            throw new InsufficientBalanceException("Insufficient funds on balance");
        }

        cardAccountRepository.saveAndFlush(
                CardAccountEntity.builder()
                        .accId(senderAccId)
                        .amountOnAcc(sender.getAmountOnAcc().subtract(transactionAmount))
                        .build()
        );

        cardAccountRepository.saveAndFlush(
                CardAccountEntity.builder()
                        .accId(payeeAccId)
                        .amountOnAcc(payee.getAmountOnAcc().add(transactionAmount))
                        .build()
        );

        TransactionEntity entity = transactionRepository.saveAndFlush(
                TransactionEntity.builder()
                        .senderAccId(sender)
                        .payeeAccId(payee)
                        .transactionAmount(transactionAmount)
                        .transactionDateTime(LocalDateTime.now())
                        .build()
        );

        return TransactionDtoFactory.makeTransactionDto(entity);
    }
}
