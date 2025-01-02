package arch.attanake.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

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
    Instant birthDate;

    @JsonProperty("secret_word")
    String secretWord;

    @JsonProperty("phone_num")
    Long phoneNum;

    String email;
}
