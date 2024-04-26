package com.dux.apis;

import com.dux.core.auth.services.AuthService;
import com.dux.core.auth.dtos.LoginRequestDTO;
import com.dux.core.auth.dtos.LoginResponseDTO;
import com.dux.core.response.ResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Autenticación")
public class AuthApi {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/login")
    @Operation(summary = "Login de usuario", description = "Autenticar un usuario y obtener un token de acceso.")
    @ApiResponse(responseCode = "200", description = "Login exitoso")
    @ApiResponse(responseCode = "400", description = "La solicitud es invalida",
            content = @Content(schema = @Schema(implementation = ResponseError.class)))
    @ApiResponse(responseCode = "401", description = "Autenticación no exitosa",
            content = @Content(schema = @Schema(implementation = ResponseError.class)))
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}

