package com.dwes.alumnos.excepcion;

public class NombreUsuarioDuplicadoException extends RuntimeException {

    public NombreUsuarioDuplicadoException(String nombreUsuario) {
        super("Ya existe un alumno con el nombre de usuario " + nombreUsuario);
    }
}
