package com.dwes.alumnos.excepcion;

public class AlumnoNoEncontradoException extends RuntimeException {

    public AlumnoNoEncontradoException(Long id) {
        super("No existe ningún alumno con id " + id);
    }
}
