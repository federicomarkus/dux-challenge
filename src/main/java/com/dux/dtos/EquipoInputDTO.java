package com.dux.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquipoInputDTO {

    @NotBlank
    private String nombre;
    @NotBlank
    private String liga;
    @NotBlank
    private String pais;
}
