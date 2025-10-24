package giuseppeperna.U5_W3_D5_Project.security;

import giuseppeperna.U5_W3_D5_Project.entities.Utente;
import giuseppeperna.U5_W3_D5_Project.exceptions.UnauthorizedException;
import giuseppeperna.U5_W3_D5_Project.services.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UtenteService utenteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Token mancante o non valido");
        }

        String token = authHeader.substring(7);
        jwtTools.verifyToken(token);

        String id = jwtTools.extractIdFromToken(token);
        Utente utente = utenteService.findById(Long.parseLong(id));

        request.setAttribute("currentUser", utente);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}

