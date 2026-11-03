package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;

public class Animal {
    private Long id;

    // aplicamos la restricción tiene que tener nombre
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    public Animal() {}
    public Animal(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
