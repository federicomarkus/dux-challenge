package com.dux.core.auth.services;

import com.dux.core.auth.dtos.LoginRequestDTO;
import com.dux.core.auth.dtos.LoginResponseDTO;
import com.dux.core.exceptions.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponseDTO login(LoginRequestDTO request) {
        log.info("AuthService.login - Username [{}]", request.getUsername());

        try {
            // Se autentica el usuario. (Se obtiene y comprueba credenciales)
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            // Vericamos si fue exitosa la autenticación.
            if (!authentication.isAuthenticated()) {
                throw ApiException.builder()
                        .estado(HttpStatus.UNAUTHORIZED)
                        .mensaje("Autenticación no exitosa")
                        .build();
            }

            return LoginResponseDTO.builder()
                    .token(tokenService.createToken(request.getUsername()))
                    .build();

        } catch (BadCredentialsException | InternalAuthenticationServiceException ex) {
            // No existe un usuario con el username o es erronea la contraseña.
            throw ApiException.builder()
                    .estado(HttpStatus.UNAUTHORIZED)
                    .mensaje("Autenticación no exitosa - Credenciales erroneas")
                    .build();
        }
    }

}
