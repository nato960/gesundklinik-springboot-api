package personal.GesundKlinik.modules.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import personal.GesundKlinik.modules.auth.dto.JwtResponse;
import personal.GesundKlinik.modules.auth.dto.LoginRequest;
import personal.GesundKlinik.modules.auth.jwt.IJwtService;
import personal.GesundKlinik.modules.user.entity.User;
import personal.GesundKlinik.shared.exception.TokenException;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{

    private final AuthenticationManager manager;
    private final IJwtService jwtService;

    public JwtResponse authenticate(LoginRequest request){
        try {
            var auth = new UsernamePasswordAuthenticationToken(request.login(), request.password());
            var result = manager.authenticate(auth);
            var user = (User) result.getPrincipal();

            return new JwtResponse(jwtService.generateToken(user));
        } catch (AuthenticationException ex) {
            throw new TokenException("Invalid login credentials", ex);
        }
    }

}
