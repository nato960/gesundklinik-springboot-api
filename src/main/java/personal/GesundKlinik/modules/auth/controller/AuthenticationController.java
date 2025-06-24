package personal.GesundKlinik.modules.auth.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.GesundKlinik.modules.auth.dto.JwtResponse;
import personal.GesundKlinik.modules.auth.dto.LoginRequest;
import personal.GesundKlinik.modules.auth.service.IAuthService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Tag(name = "Authentication")
public class AuthenticationController {

    private final IAuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest request){
        var jwt = authService.authenticate(request);
        return ResponseEntity.ok(jwt);
    }

}
