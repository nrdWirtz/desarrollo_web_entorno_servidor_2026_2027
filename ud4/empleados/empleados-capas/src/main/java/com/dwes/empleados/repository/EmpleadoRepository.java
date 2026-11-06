package com.dwes.empleados.repository;

import java.util.List;

import com.dwes.empleados.model.Empleado;

public interface EmpleadoRepository {

    List<Empleado> findAll();

    List<Empleado> findByNombreContainingIgnoreCase(String texto);

    // Devuelve null cuando no existe un empleado con ese identificador.
    Empleado findById(Long id);

    // Devuelve null cuando no existe un empleado con ese email.
    Empleado findByEmail(String email);

    Empleado save(Empleado empleado);

    void delete(Empleado empleado);
}
