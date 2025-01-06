package arch.attanake.api.factroies;

import arch.attanake.api.dto.TransactionDto;
import arch.attanake.store.entities.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoFactory {

    public static TransactionDto makeTransactionDto(TransactionEntity entity){

        return TransactionDto.builder()
                .transactionId(entity.getTransactionId())
                .transactionAmount(entity.getTransactionAmount())
                .senderAccId(entity.getSenderAccId())
                .payeeAccId(entity.getPayeeAccId())
                .transactionDateTime(entity.getTransactionDateTime())
                .build();
    }
}
