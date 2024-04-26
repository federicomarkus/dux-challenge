package com.dux.repositories;

import com.dux.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    // Primero comprobamos si es igual o empieza por el nombre buscado para aprovechar el index y luego buscamos si lo contiene.
    @Query(value = "SELECT eq from Equipo eq WHERE eq.nombre LIKE CONCAT(:nombre, '%') OR eq.nombre LIKE CONCAT('%', :nombre, '%')")
    List<Equipo> buscarPorNombre(@Param("nombre") String nombre);

}
