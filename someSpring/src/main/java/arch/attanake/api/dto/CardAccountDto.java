package arch.attanake.api.dto;

import arch.attanake.store.entities.CardAccountTypeEntity;
import arch.attanake.store.entities.Currencies;
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
public class CardAccountDto {

    @JsonProperty("acc_id")
    Long accId;

    @JsonProperty("acc_type")
    CardAccountTypeEntity accType;

    @JsonProperty("amount_on_acc")
    BigDecimal amountOnAcc;

    @JsonProperty("acc_currency")
    Currencies accCurrency;

    @JsonProperty("acc_term")
    Integer accTerm;

    @JsonProperty("calculation_interval")
    LocalDateTime calculationInterval;
}
