package personal.GesundKlinik.modules.pacient.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import personal.GesundKlinik.shared.dto.AddressDto;

public record UpdatePacientRequest(
                                   @JsonProperty("name")
                                   String name,
                                   @Email
                                   @JsonProperty("email")
                                   String email,
                                   @JsonProperty("phone")
                                   String phone,
                                   @JsonProperty("address")
                                   AddressDto address) {
}
