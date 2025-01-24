package arch.attanake.api.controllers;

import arch.attanake.api.dto.TransactionDto;
import arch.attanake.api.exceptions.BadRequestException;
import arch.attanake.api.exceptions.InsufficientBalanceException;
import arch.attanake.api.factroies.CardAccountDtoFactory;
import arch.attanake.api.factroies.ClientDtoFactory;
import arch.attanake.api.factroies.TransactionDtoFactory;
import arch.attanake.store.entities.CardAccountEntity;
import arch.attanake.store.entities.ClientEntity;
import arch.attanake.store.entities.TransactionEntity;
import arch.attanake.store.repositories.CardAccountRepository;
import arch.attanake.store.repositories.ClientRepository;
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
    private final ClientRepository clientRepository;

    private static final String CREATE_TRANSACTION = "/api/transactions";

    @PostMapping(CREATE_TRANSACTION)
    public TransactionDto createTransaction(@RequestParam("sender_acc_id") Long senderAccId,
                                            @RequestParam("payee_acc_id") Long payeeAccId,
                                            @RequestParam("transaction_amount") BigDecimal transactionAmount){

        if(transactionAmount.intValue()<= 0){
            throw new BadRequestException("Transaction amount is lower than required");
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

        return TransactionDtoFactory.makeTransactionDto(transactionEntity);
    }
}
