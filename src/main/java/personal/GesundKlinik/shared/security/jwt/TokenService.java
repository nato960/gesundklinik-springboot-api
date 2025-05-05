//package personal.GesundKlinik.shared.security.jwt;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//
//@Service

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

//@RequiredArgsConstructor
//public class TokenService {
//
//    private static final String ISSUER = "API Voll.med";
//
//    @Value("${api.security.token.secret}")
//    private String secret;
//
//    public String gerarToken(Usuario usuario){
//        try {
//            var algoritmo = Algorithm.HMAC256(secret);
//            return JWT.create()
//                    .withIssuer(ISSUER)
//                    .withSubject(usuario.getLogin())
//                    .withClaim("id", usuario.getId())
//                    .withExpiresAt(dataExpiracao())
//                    .sign(algoritmo);
//        } catch (JWTCreationException exception){
//            throw new RuntimeException("erro ao gerar o token jwt", exception);
//        }
//    }
//
//    public String getSubject(String tokenJWT){
//        try {
//            var algoritmo = Algorithm.HMAC256(secret);
//            return JWT.require(algoritmo)
//                    .withIssuer(ISSUER)
//                    .build()
//                    .verify(tokenJWT)
//                    .getSubject();
//        } catch (JWTVerificationException exception){
//            throw new RuntimeException("Token inválido ou expirado! " +tokenJWT);
//        }
//    }
//
//    private Instant dataExpiracao() {
//        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
//    }
//}
