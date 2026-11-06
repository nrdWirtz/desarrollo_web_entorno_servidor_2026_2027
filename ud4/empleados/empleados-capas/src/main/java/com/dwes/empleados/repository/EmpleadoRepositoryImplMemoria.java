package com.dwes.empleados.repository;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Repository;

import com.dwes.empleados.model.Empleado;

@Repository
public class EmpleadoRepositoryImplMemoria implements EmpleadoRepository {

    // El listado empieza vacío. Al cerrar la aplicación se pierden los datos.
    private final List<Empleado> empleados = new ArrayList<>();
    private long siguienteId = 1;

    @Override
    public List<Empleado> findAll() {
        return empleados;
    }

    @Override
    public List<Empleado> findByNombreContainingIgnoreCase(String texto) {
        List<Empleado> resultado = new ArrayList<>();
        String textoBuscado = texto.toLowerCase();
        for (Empleado empleado : empleados) {
            if (empleado.getNombre().toLowerCase().contains(textoBuscado)) {
                resultado.add(empleado);
            }
        }

        return resultado;
    }

    @Override
    public Empleado findById(Long id) {
        for (Empleado empleado : empleados) {
            if (empleado.getId().equals(id)) {
                return empleado;
            }
        }
        return null;
    }

    @Override
    public Empleado findByEmail(String email) {
        for (Empleado empleado : empleados) {
            if (empleado.getEmail().equals(email)) {
                return empleado;
            }
        }
        return null;
    }

    @Override
    public Empleado save(Empleado empleado) {
        if (empleado.getId() == null) {
            empleado.setId(siguienteId++);
            empleados.add(empleado);
            return empleado;
        }

        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getId().equals(empleado.getId())) {
                empleados.set(i, empleado);
                return empleado;
            }
        }

        // El servicio comprueba antes que el empleado exista.
        throw new IllegalArgumentException("No se puede guardar un identificador inexistente");
    }

    @Override
    public void delete(Empleado empleado) {
        empleados.remove(empleado);
    }
}
