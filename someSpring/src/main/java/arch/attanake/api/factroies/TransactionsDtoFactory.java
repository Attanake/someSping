package arch.attanake.api.factroies;

import arch.attanake.api.dto.TransactionsDto;
import arch.attanake.store.entities.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionsDtoFactory {

    public TransactionsDto makeTransactionDto(TransactionEntity entity){

        return TransactionsDto.builder()
                .transactionId(entity.getTransactionId())
                .transactionAmount(entity.getTransactionAmount())
                .senderAccId(entity.getSenderAccId())
                .payeeAccId(entity.getPayeeAccId())
                .transactionDateTime(entity.getTransactionDateTime())
                .build();
    }
}
