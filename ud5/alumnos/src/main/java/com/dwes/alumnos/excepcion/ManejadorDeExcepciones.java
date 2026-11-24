package com.dwes.alumnos.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ManejadorDeExcepciones {

    @ExceptionHandler(AlumnoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String alumnoNoEncontrado(AlumnoNoEncontradoException ex, Model model) {
        model.addAttribute("mensajeNotFound", ex.getMessage());
        return "error/404";
    }
}
