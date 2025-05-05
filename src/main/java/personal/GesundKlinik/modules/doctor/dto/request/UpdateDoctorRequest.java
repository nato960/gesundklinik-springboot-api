package personal.GesundKlinik.modules.doctor.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import personal.GesundKlinik.shared.dto.AddressDto;

public record UpdateDoctorRequest(
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
