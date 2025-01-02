package arch.attanake.api.dto;

import arch.attanake.store.entities.AccountType;
import arch.attanake.store.entities.Currencies;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardAccountDto {

    @JsonProperty("acc_id")
    Long accId;

    @JsonProperty("acc_type")
    AccountType accType;

    @JsonProperty("amount_on_acc")
    BigDecimal amountOnAcc;

    @JsonProperty("acc_currency")
    Currencies accCurrency;

    @JsonProperty("acc_interest_term")
    Float accInterestTerm;

    @JsonProperty("acc_term")
    Instant accTerm;

    @JsonProperty("calculation_interval")
    Instant calculationInterval;
}
