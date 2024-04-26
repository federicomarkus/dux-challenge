package com.dux.core.auth.services;

import com.dux.core.exceptions.ApiException;
import com.dux.model.Usuario;
import com.dux.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Implementación para buscar el usuario para la autenticación.
     * @param username Nombre del usuario.
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) {

        // Buscamos el usuario para la autenticación.
        Usuario user = usuarioRepository.findByUsuario(username).orElseThrow(() -> ApiException.builder().estado(HttpStatus.NOT_FOUND).build());

        return User.builder()
                .username(user.getUsuario())
                .password(user.getPassword())
                .build();
    }

}
