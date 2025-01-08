package arch.attanake.api.dto;

import arch.attanake.store.entities.CardAccountEntity;
import arch.attanake.store.entities.CreditEntity;
import arch.attanake.store.entities.TransactionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientDto {

    @JsonProperty("client_id")
    Long clientId;

    String identificationNum;

    @JsonProperty("second_name")
    String secondName;

    String name;

    String surname;

    @JsonProperty("birth_date")
    LocalDate birthDate;

    @JsonProperty("secret_word")
    String secretWord;

    @JsonProperty("phone_num")
    Long phoneNum;

    String email;

    List<CardAccountEntity> cardAccounts;

    List<CreditEntity> credits;

    List<TransactionEntity> transactions;
}
