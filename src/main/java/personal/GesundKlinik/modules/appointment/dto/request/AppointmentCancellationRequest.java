package personal.GesundKlinik.modules.appointment.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import personal.GesundKlinik.modules.appointment.entity.CancellationReason;

public record AppointmentCancellationRequest(
        @JsonProperty("cancellation_reason")
        CancellationReason cancellationReason) {
}
