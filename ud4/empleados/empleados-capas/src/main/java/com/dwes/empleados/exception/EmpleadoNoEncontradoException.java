package com.dwes.empleados.exception;

public class EmpleadoNoEncontradoException extends RuntimeException {

    public EmpleadoNoEncontradoException(Long id) {
        super("No existe ningún empleado con el identificador " + id);
    }
}
