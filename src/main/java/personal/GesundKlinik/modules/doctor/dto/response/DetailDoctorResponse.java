package personal.GesundKlinik.modules.doctor.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import personal.GesundKlinik.modules.doctor.entity.Speciality;
import personal.GesundKlinik.shared.dto.AddressDto;

import java.time.LocalDate;

public record DetailDoctorResponse(
                                @JsonProperty("id")
                                Long id,
                                @JsonProperty("name")
                                String name,
                                @JsonProperty("email")
                                String email,
                                @JsonProperty("crm")
                                String crm,
                                @JsonProperty("birthDate")
                                LocalDate birthDate,
                                @JsonProperty("phone")
                                String phone,
                                @JsonProperty("speciality")
                                Speciality speciality,
                                @JsonProperty("address")
                                AddressDto address) {
}
