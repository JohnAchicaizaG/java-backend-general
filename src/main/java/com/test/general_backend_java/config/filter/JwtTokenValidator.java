package com.test.general_backend_java.config.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.test.general_backend_java.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JwtTokenValidator extends OncePerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtTokenValidator.class.getName());

    private final JwtUtil jwtUtil;

    public JwtTokenValidator(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // No aplicar validación de token a rutas públicas
        if (path.startsWith("/users/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
            try {
                DecodedJWT decodedJWT = jwtUtil.validateToken(jwtToken);
                String username = jwtUtil.getUserName(decodedJWT);

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTVerificationException e) {
                LOGGER.log(Level.WARNING, "Token validation failed: {0}", e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid Token");
                return; // Terminar el filtro si el token es inválido
            }
        } else {
            LOGGER.log(Level.WARNING, "No token found or invalid format");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token Missing or Invalid Format");
            return; // Terminar el filtro si el token no está presente
        }

        filterChain.doFilter(request, response);
    }
}