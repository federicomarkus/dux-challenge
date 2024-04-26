package com.dux.services;

import com.dux.core.exceptions.ApiException;
import com.dux.dtos.EquipoInputDTO;
import com.dux.model.Equipo;
import com.dux.repositories.EquipoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    public List<Equipo> obtenerEquipos() {
        log.info("EquipoService.obtenerEquipos");

        return equipoRepository.findAll();
    }

    public Equipo buscarEquipoPorId(Long id) {
        log.info("EquipoService.buscarEquipoPorId - ID [{}]", id);

        if (id == null) throw ApiException.builder().estado(HttpStatus.BAD_REQUEST).mensaje("El ID del equipo no puede ser nulo").build();

        return equipoRepository.findById(id).orElseThrow(() -> ApiException.builder()
                .estado(HttpStatus.NOT_FOUND)
                .mensaje("Equipo no encontrado")
                .build());
    }

    public List<Equipo> buscarEquiposPorNombre(String nombre) {
        log.info("EquipoService.buscarEquiposPorNombre - Nombre [{}]", nombre);

        // Comprobamos que si el nombre es nulo o vacio para no hacer la consulta y devolver una lista vacia.
        if (StringUtils.isBlank(nombre)) {
            log.warn("EquipoService.buscarEquiposPorNombre - El nombre proporcionado está vacío o nulo. Retornando una lista vacía.");
            return new ArrayList<>();
        }

        return equipoRepository.buscarPorNombre(nombre);
    }

    @Transactional
    public Equipo crearEquipo(EquipoInputDTO equipoInput) {
        log.info("EquipoService.crearEquipo - Input [{}]", equipoInput);

        if (equipoInput == null) throw ApiException.builder().estado(HttpStatus.BAD_REQUEST).mensaje("El input no puede ser nulo para la creación").build();

        Equipo equipo = Equipo.builder()
                .nombre(equipoInput.getNombre())
                .liga(equipoInput.getLiga())
                .pais(equipoInput.getPais())
                .build();

        equipoRepository.save(equipo);

        return equipo;
    }

    @Transactional
    public Equipo actualizarEquipo(Long id, EquipoInputDTO equipoInput) {
        log.info("EquipoService.actualizarEquipo - ID [{}] Input [{}]", id, equipoInput);

        Equipo equipo = this.buscarEquipoPorId(id);

        // Comprobamos de no pisar si viene vacio o nulo el dato en el input.

        if (equipoInput != null) {
            if (StringUtils.isNotBlank(equipoInput.getLiga())) {
                equipo.setLiga(equipoInput.getLiga());
            }
            if (StringUtils.isNotBlank(equipoInput.getNombre())) {
                equipo.setNombre(equipoInput.getNombre());
            }
            if (StringUtils.isNotBlank(equipoInput.getPais())) {
                equipo.setPais(equipoInput.getPais());
            }

            equipoRepository.save(equipo);
        }

        return equipo;
    }

    @Transactional
    public void eliminarEquipo(Long id) {
        log.info("EquipoService.eliminarEquipo - ID [{}]", id);

        Equipo equipo = this.buscarEquipoPorId(id);
        equipoRepository.delete(equipo);
    }
}
