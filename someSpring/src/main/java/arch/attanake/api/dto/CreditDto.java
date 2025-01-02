package arch.attanake.api.dto;

import arch.attanake.store.entities.LoanType;
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
public class CreditDto {

    @JsonProperty("credit_id")
    Long creditId;

    @JsonProperty("loan_type")
    LoanType loanType;

    @JsonProperty("interest_rate")
    Float interestRate;

    @JsonProperty("loan_term")
    Instant loanTerm;

    @JsonProperty("total_amount")
    BigDecimal totalAmount;

    @JsonProperty("loan_balance")
    BigDecimal loanBalance;

    @JsonProperty("loan_issue_date")
    Instant loanIssueDate;

    @JsonProperty("monthly_fee")
    BigDecimal monthlyFee;

    @JsonProperty("final_fee_date")
    Instant finalFeeDate;
}
