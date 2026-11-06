package com.dwes.empleados.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dwes.empleados.exception.EmpleadoNoEncontradoException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String empleadoNoEncontrado(EmpleadoNoEncontradoException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error/404";
    }
}
