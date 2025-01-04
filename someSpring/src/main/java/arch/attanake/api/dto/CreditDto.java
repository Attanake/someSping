package arch.attanake.api.dto;

import arch.attanake.store.entities.LoanTypeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditDto {

    @JsonProperty("credit_id")
    Long creditId;

    @JsonProperty("loan_type")
    LoanTypeEntity loanTypeEntity;

    @JsonProperty("interest_rate")
    Float interestRate;

    @JsonProperty("loan_term")
    Instant loanTerm;

    @JsonProperty("total_amount")
    BigDecimal totalAmount;

    @JsonProperty("loan_balance")
    BigDecimal loanBalance;

    @JsonProperty("loan_issue_date")
    LocalDateTime loanIssueDate;

    @JsonProperty("monthly_fee")
    BigDecimal monthlyFee;

    @JsonProperty("final_fee_date")
    LocalDateTime finalFeeDate;
}
