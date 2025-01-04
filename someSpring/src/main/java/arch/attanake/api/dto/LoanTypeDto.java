package arch.attanake.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanTypeDto {

    @JsonProperty("loan_type_id")
    private Long loanTypeId;

    @JsonProperty("loan_type")
    private String loanType;

    @JsonProperty("interest_rate")
    private Float interestRate;

    @JsonProperty("max_loan_term")
    private Integer maxLoanTerm;

    @JsonProperty("min_loan_term")
    private Integer minLoanTerm;
}
