package personal.GesundKlinik.modules.doctor.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import personal.GesundKlinik.modules.doctor.entity.Speciality;
import personal.GesundKlinik.shared.dto.AddressDto;

import java.time.LocalDate;

public record SaveDoctorRequest(@NotBlank
                                @JsonProperty("name")
                                String name,
                                @NotBlank
                                @Email
                                @JsonProperty("email")
                                String email,
                                @NotBlank
                                @JsonProperty("crm")
                                String crm,
                                @NotNull
                                @JsonProperty("birthDate")
                                LocalDate birthDate,
                                @NotBlank
                                @JsonProperty("phone")
                                String phone,
                                @NotNull
                                @JsonProperty("speciality")
                                Speciality speciality,
                                @NotNull
                                @Valid
                                @JsonProperty("address")
                                AddressDto address) {
}
