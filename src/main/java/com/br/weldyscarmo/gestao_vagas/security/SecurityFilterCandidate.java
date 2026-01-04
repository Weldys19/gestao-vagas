package com.br.weldyscarmo.gestao_vagas.security;

import com.br.weldyscarmo.gestao_vagas.providers.JWTProviderCandidate;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class SecurityFilterCandidate extends OncePerRequestFilter {

    @Autowired
    private JWTProviderCandidate jwtProviderCandidate;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain)
            throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(null);
        var header = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/candidate")) {
            if (header != null) {
                var tokenDecoded = this.jwtProviderCandidate.validateToken(header);
                if (tokenDecoded == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                request.setAttribute("candidate_id", tokenDecoded.getSubject());
                System.out.println(tokenDecoded);
            }
        }

        filterChain.doFilter(request, response);

    }
}
