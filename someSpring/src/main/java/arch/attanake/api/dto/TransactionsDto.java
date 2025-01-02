package arch.attanake.api.dto;

import arch.attanake.store.entities.CardAccountEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionsDto {

    @JsonProperty("tr_id")
    Long transactionId;

    @JsonProperty("sender_acc_id")
    CardAccountEntity senderAccId;

    @JsonProperty("payee_acc_id")
    CardAccountEntity payeeAccId;

    @JsonProperty("tr_amount")
    BigDecimal transactionAmount;

    @JsonProperty("tr_date_time")
    Instant transactionDateTime;
}
