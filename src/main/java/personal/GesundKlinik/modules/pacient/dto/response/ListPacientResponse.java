package personal.GesundKlinik.modules.pacient.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import personal.GesundKlinik.shared.dto.AddressDto;

import java.time.LocalDate;

public record ListPacientResponse(@JsonProperty("id")
                                  Long id,
                                  @JsonProperty("name")
                                  String name,
                                  @JsonProperty("email")
                                  String email,
                                  @JsonProperty("cpf")
                                  String cpf,
                                  @JsonProperty("birthDate")
                                  LocalDate birthDate,
                                  @JsonProperty("phone")
                                  String phone,
                                  @JsonProperty("address")
                                  AddressDto address) {
}
