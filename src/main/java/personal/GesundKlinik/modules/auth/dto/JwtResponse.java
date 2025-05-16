package personal.GesundKlinik.modules.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JwtResponse(
        @JsonProperty("token")
        String token,
        @JsonProperty("type")
        String type) {

    public JwtResponse(String token) {
        this(token, "Bearer");
    }
}
