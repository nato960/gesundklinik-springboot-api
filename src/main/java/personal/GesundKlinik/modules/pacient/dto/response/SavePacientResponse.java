package personal.GesundKlinik.modules.pacient.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import personal.GesundKlinik.shared.dto.AddressDto;

import java.time.LocalDate;

public record SavePacientResponse(@JsonProperty("id")
                                  Long id,
                                  @JsonProperty("name")
                                  String name,
                                  @JsonProperty("email")
                                  String email,
                                  @JsonProperty("cpf")
                                  String cpf,
                                  @JsonProperty("birth_date")
                                  @JsonFormat(pattern = "yyyy-MM-dd")
                                  LocalDate birthDate,
                                  @JsonProperty("phone")
                                  String phone,
                                  @JsonProperty("address")
                                  AddressDto address) {
}
