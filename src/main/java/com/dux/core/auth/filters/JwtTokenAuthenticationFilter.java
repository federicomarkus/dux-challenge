package com.dux.core.auth.filters;

import com.dux.core.auth.services.CustomUserDetailsService;
import com.dux.core.auth.services.TokenService;
import com.dux.core.exceptions.ApiException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {

        try {
            // Se intenta obtener el header 'Authorization' para obtener el token.
            String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Si existe, obtenemos el token y de él, el username para la autenticación (se comprueba si el token no expiro).
            String token = authHeader.substring(7);
            String username = tokenService.getIdFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Se establece la autenticación del usuario en el contexto de seguridad.
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, token, new ArrayList<>());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);
        } catch (ApiException ex){
            response.setStatus(ex.getEstado().value());
            if (StringUtils.isNotBlank(ex.getMensaje())) {
                response.getWriter().write(ex.getMensaje());
            }
        } catch (Exception ex){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

    }
}