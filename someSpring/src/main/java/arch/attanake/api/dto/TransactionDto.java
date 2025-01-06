package arch.attanake.api.dto;

import arch.attanake.store.entities.CardAccountEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDto {

    @JsonProperty("tr_id")
    Long transactionId;

    @JsonProperty("sender_acc_id")
    CardAccountEntity senderAccId;

    @JsonProperty("payee_acc_id")
    CardAccountEntity payeeAccId;

    @JsonProperty("tr_amount")
    BigDecimal transactionAmount;

    @JsonProperty("tr_date_time")
    LocalDateTime transactionDateTime;
}
