package personal.GesundKlinik.shared.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import personal.GesundKlinik.modules.user.repository.IUserRepository;
import personal.GesundKlinik.modules.auth.jwt.IJwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {


    private final IJwtService jwtService;

    private final IUserRepository repository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var jwtToken = extractToken(request);

        if (jwtToken != null) {
            var subject = jwtService.getSubject(jwtToken);
            var user = repository.findByLogin(subject);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Logged in for the request");
        }

        filterChain.doFilter(request, response);

    }

    private String extractToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null){
                return authorizationHeader.replace("Bearer ", "").trim();
            }
            return null;

    }
}
