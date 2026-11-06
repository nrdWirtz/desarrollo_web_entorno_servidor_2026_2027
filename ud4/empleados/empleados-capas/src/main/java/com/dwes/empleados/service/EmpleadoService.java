package com.dwes.empleados.service;

import java.util.List;

import com.dwes.empleados.model.Empleado;

public interface EmpleadoService {

    List<Empleado> listar(String busqueda);

    Empleado buscarPorId(Long id);

    Empleado crear(Empleado empleado);

    Empleado actualizar(Long id, Empleado empleado);

    void eliminar(Long id);
}
