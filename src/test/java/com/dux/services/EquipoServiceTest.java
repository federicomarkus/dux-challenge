package com.dux.services;

import com.dux.core.exceptions.ApiException;
import com.dux.dtos.EquipoInputDTO;
import com.dux.model.Equipo;
import com.dux.repositories.EquipoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EquipoServiceTest {

    @InjectMocks
    private EquipoService equipoService;

    @Mock
    private EquipoRepository equipoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_obtenerEquipos() {
        List<Equipo> equipos = new ArrayList<>();
        equipos.add(Equipo.builder().id(1L).pais("España").liga("La Liga").nombre("Real Madrid").build());
        equipos.add(Equipo.builder().id(2L).pais("España").liga("La Liga").nombre("FC Barcelona").build());
        equipos.add(Equipo.builder().id(3L).pais("Inglaterra").liga("Premier League").nombre("Manchester United").build());

        when(equipoRepository.findAll()).thenReturn(equipos);

        List<Equipo> response = equipoService.obtenerEquipos();

        assertEquals(equipos, response);

        verify(equipoRepository).findAll();
    }

    @Test
    void test_buscarEquipoPorId() {
        Long id = 1L;
        Equipo equipo = Equipo.builder().id(id).pais("España").liga("La Liga").nombre("Real Madrid").build();

        when(equipoRepository.findById(id)).thenReturn(Optional.of(equipo));

        Equipo response = equipoService.buscarEquipoPorId(id);

        assertEquals(equipo, response);

        verify(equipoRepository).findById(id);
    }

    @Test
    void test_buscarEquipoPorId_notfound() {
        Long id = 1L;

        when(equipoRepository.findById(id)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> equipoService.buscarEquipoPorId(id));

        assertEquals(HttpStatus.NOT_FOUND, exception.getEstado());

        verify(equipoRepository).findById(id);
    }

    @Test
    void test_buscarEquipoPorId_id_null() {
        Long id = null;

        ApiException exception = assertThrows(ApiException.class, () -> equipoService.buscarEquipoPorId(id));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getEstado());

        verify(equipoRepository, never()).findById(any());
    }

    @Test
    void test_buscarEquiposPorNombre() {
        String nombre = "Barcelona";

        List<Equipo> equipos = new ArrayList<>();
        equipos.add(Equipo.builder().id(2L).pais("España").liga("La Liga").nombre("FC Barcelona").build());

        when(equipoRepository.buscarPorNombre(nombre)).thenReturn(equipos);

        List<Equipo> response = equipoService.buscarEquiposPorNombre(nombre);

        assertEquals(equipos, response);

        verify(equipoRepository).buscarPorNombre(nombre);
    }

    @Test
    void test_buscarEquiposPorNombre_nombre_nulo() {
        String nombre = null;

        List<Equipo> response = equipoService.buscarEquiposPorNombre(nombre);
        assertEquals(new ArrayList<>(), response);

        verify(equipoRepository, never()).buscarPorNombre(any());
    }

    @Test
    void test_crearEquipo() {
        EquipoInputDTO inputDTO = new EquipoInputDTO();
        inputDTO.setNombre("Real Madrid");
        inputDTO.setLiga("La Liga");
        inputDTO.setPais("España");

        Equipo response = equipoService.crearEquipo(inputDTO);

        assertNotNull(response);
        assertEquals("Real Madrid", response.getNombre());
        assertEquals("La Liga", response.getLiga());
        assertEquals("España", response.getPais());

        verify(equipoRepository, times(1)).save(any(Equipo.class));
    }

    @Test
    void test_crearEquipo_input_null() {
        ApiException exception = assertThrows(ApiException.class, () -> equipoService.crearEquipo(null));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getEstado());

        verify(equipoRepository,never()).save(any(Equipo.class));
    }

    @Test
    void test_actualizarEquipo() {
        Long id = 1L;
        EquipoInputDTO inputDTO = new EquipoInputDTO();
        inputDTO.setNombre("Real Madrid Club de Fútbol");
        inputDTO.setPais("Espania");
        inputDTO.setLiga("LaLiga");

        Equipo equipo = Equipo.builder().id(id).pais("España").liga("La Liga").nombre("Real Madrid").build();

        when(equipoRepository.findById(id)).thenReturn(Optional.of(equipo));

        Equipo response = equipoService.actualizarEquipo(id, inputDTO);

        assertNotNull(response);
        assertEquals(inputDTO.getNombre(), response.getNombre());
        assertEquals(inputDTO.getLiga(), response.getLiga());
        assertEquals(inputDTO.getPais(), response.getPais());

        verify(equipoRepository, times(1)).save(any(Equipo.class));
    }

    @Test
    void test_actualizarEquipo_input_null() {
        Long id = 1L;

        Equipo equipo = Equipo.builder().id(id).pais("España").liga("La Liga").nombre("Real Madrid").build();

        when(equipoRepository.findById(id)).thenReturn(Optional.of(equipo));

        Equipo response = equipoService.actualizarEquipo(id, null);

        assertNotNull(response);
        assertEquals(equipo.getNombre(), response.getNombre());
        assertEquals(equipo.getLiga(), response.getLiga());
        assertEquals(equipo.getPais(), response.getPais());

        verify(equipoRepository, never()).save(any(Equipo.class));
    }

    @Test
    void test_eliminarEquipo() {
        Long id = 1L;

        Equipo equipo = Equipo.builder().id(id).pais("España").liga("La Liga").nombre("Real Madrid").build();

        when(equipoRepository.findById(id)).thenReturn(Optional.of(equipo));

        assertDoesNotThrow(() -> equipoService.eliminarEquipo(id));

        verify(equipoRepository, times(1)).delete(any(Equipo.class));
    }
}