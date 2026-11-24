package com.dwes.alumnos.servicio;

import com.dwes.alumnos.domain.Alumno;

import java.util.List;

public interface AlumnoServicio {

    List<Alumno> listar(String filtroNombre);

    Alumno buscarPorId(Long id);

    Alumno crear(Alumno alumno);

    Alumno actualizar(Long id, Alumno datos);

    Alumno activar(Long id);

    Alumno desactivar(Long id);

    void eliminar(Long id);
}
