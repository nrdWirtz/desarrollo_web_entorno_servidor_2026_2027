package com.dwes.alumnos.repositorio;

import com.dwes.alumnos.domain.Alumno;

import java.util.List;
import java.util.Optional;

public interface AlumnoRepositorio {

    List<Alumno> findAll();

    List<Alumno> findByNombreContainingIgnoreCase(String nombre);

    Optional<Alumno> findById(Long id);

    Optional<Alumno> findByNombreUsuarioIgnoreCase(String nombreUsuario);

    Alumno save(Alumno alumno);

    void deleteById(Long id);
}
