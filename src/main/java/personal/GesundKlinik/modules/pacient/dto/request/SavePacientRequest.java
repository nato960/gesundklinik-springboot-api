package personal.GesundKlinik.modules.pacient.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import personal.GesundKlinik.shared.dto.AddressDto;

import java.time.LocalDate;

public record SavePacientRequest(@NotBlank
                                 @JsonProperty("name")
                                 String name,
                                 @NotBlank
                                 @Email
                                 @JsonProperty("email")
                                 String email,
                                 @NotBlank
                                 @JsonProperty("cpf")
                                 String cpf,
                                 @NotNull
                                 @JsonProperty("birth_date")
                                 @JsonFormat(pattern = "yyyy-MM-dd")
                                 LocalDate birthDate,
                                 @NotBlank
                                 @JsonProperty("phone")
                                 String phone,
                                 @NotNull
                                 @Valid
                                 @JsonProperty("address")
                                 AddressDto address) {
}
