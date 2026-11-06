package com.example.huerto.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionManager {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String resourceNotFoundHandler(ResourceNotFoundException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }
}
