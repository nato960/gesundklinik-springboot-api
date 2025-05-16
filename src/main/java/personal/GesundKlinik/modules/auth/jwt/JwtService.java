package personal.GesundKlinik.modules.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.RequiredArgsConstructor;
import personal.GesundKlinik.modules.user.entity.User;
import personal.GesundKlinik.shared.exception.TokenException;

@Service
@RequiredArgsConstructor
public class JwtService implements IJwtService{

    private static final String ISSUER = "GesundKlinik API";

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            var algoritm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getLogin())
                    .withClaim("id", user.getId())
                    .withExpiresAt(expirationDate())
                    .sign(algoritm);
        } catch (JWTCreationException exception){
            throw new TokenException("Error generating JWT token", exception);
        }
    }

    public String getSubject(String jwtToken) {
        try {
            var algoritm = Algorithm.HMAC256(secret);
            return JWT.require(algoritm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(jwtToken)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new TokenException("Invalid or expired token! " + jwtToken);
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
