package gruppo4.ALDAPAMA.security;

import gruppo4.ALDAPAMA.entities.Utente;
import gruppo4.ALDAPAMA.exceptions.UnauthorizedException;
import gruppo4.ALDAPAMA.services.UtentiService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UtentiService utentiService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Please insert a valid Bearer token");
        }

        String token = authHeader.substring(7);
        jwtTools.verifyToken(token);

        String id = jwtTools.extractIdFromToken(token);
        Utente currentUser = utentiService.getById(Long.parseLong(id));

        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return new AntPathMatcher().match("/auth/**", request.getServletPath()) ||
                new AntPathMatcher().match("/v3/api-docs/**", request.getServletPath()) ||
                new AntPathMatcher().match("/swagger-ui/**", request.getServletPath());
    }
}
