package com.dux.apis;

import com.dux.core.response.ResponseError;
import com.dux.dtos.EquipoInputDTO;
import com.dux.model.Equipo;
import com.dux.services.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Equipos")
@RestController
@RequestMapping("/equipos")
public class EquipoApi {

    @Autowired
    private EquipoService equipoService;

    @GetMapping(path = "/")
    @Operation(summary = "Obtener Equipos", description = "Obtener una lista con todos los equipos.")
    public ResponseEntity<List<Equipo>> obtenerEquipos() {
        List<Equipo> equipos = equipoService.obtenerEquipos();
        return ResponseEntity.ok(equipos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener Equipo por ID", description = "Obtener un equipo a partir de su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo Encontrado"),
            @ApiResponse(responseCode = "400", description = "El ID no puede ser nulo",
                    content = { @Content(schema = @Schema(implementation = ResponseError.class)) }),
            @ApiResponse(responseCode = "404", description = "No se encontró el equipo",
                    content = { @Content(schema = @Schema(implementation = ResponseError.class)) })
    })
    public ResponseEntity<Equipo> buscarEquipoPorId(@PathVariable Long id) {
        Equipo equipo = equipoService.buscarEquipoPorId(id);
        return ResponseEntity.ok(equipo);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar Equipos por Nombre", description = "Buscar equipos por su nombre (Contiene).")
    public ResponseEntity<List<Equipo>> buscarEquiposPorNombre(@RequestParam(name = "nombre") String nombre) {
        List<Equipo> equipos = equipoService.buscarEquiposPorNombre(nombre);
        return ResponseEntity.ok(equipos);
    }

    @PostMapping("/")
    @Operation(summary = "Crear Equipo", description = "Crear un nuevo equipo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipo creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "La solicitud es invalida",
                    content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    public ResponseEntity<Equipo> crearEquipo(@RequestBody @Valid EquipoInputDTO equipoInput) {
        Equipo equipo = equipoService.crearEquipo(equipoInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Equipo", description = "Actualizar un equipo existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el equipo",
                    content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    public ResponseEntity<Equipo> actualizarEquipo(@PathVariable Long id,
                                                   @RequestBody EquipoInputDTO equipoInput) {
        Equipo equipo = equipoService.actualizarEquipo(id, equipoInput);
        return ResponseEntity.ok(equipo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Equipo", description = "Eliminar un equipo existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el equipo",
                    content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    public ResponseEntity<?> eliminarEquipo(@PathVariable Long id) {
        equipoService.eliminarEquipo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}