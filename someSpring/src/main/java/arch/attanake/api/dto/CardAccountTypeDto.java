package arch.attanake.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardAccountTypeDto {

    @JsonProperty("acc_type_id")
    private Long accTypeId;

    @JsonProperty("acc_type_name")
    private String accType;

    @JsonProperty("interest_rate")
    private Float interestRate;

    @JsonProperty("calculation_interval")
    private Integer calculationInterval;

    @JsonProperty("max_acc_term")
    private Integer maxAccTerm;

    @JsonProperty("min_acc_term")
    private Integer minAccTerm;
}
