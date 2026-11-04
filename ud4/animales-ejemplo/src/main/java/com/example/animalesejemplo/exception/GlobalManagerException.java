package com.example.animalesejemplo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalManagerException {
    //métodos que atienden a excepciones de forma global cuando no son capturadas en un controlador
    // podemos tener tantos métodos como excepciones queramos tratar de forma global
    // si el controlador las intercepta en un try-catch no llegan aquí
    @ExceptionHandler(ResourceNotFoundException.class)  // se encarga de la excepción anotada: ResourceNotFoundException
    @ResponseStatus(HttpStatus.NOT_FOUND)   //devuelve un código de estado 404 en lugar de 200
    public String resourceNotFoundHandler(ResourceNotFoundException mensaje, Model model) {
        model.addAttribute("mensaje", mensaje.getMessage());
        return "notFound";
    }
}
