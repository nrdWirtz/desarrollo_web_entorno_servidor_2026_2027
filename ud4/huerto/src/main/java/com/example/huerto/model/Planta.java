package com.example.huerto.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Planta {

    private Long id;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "La cantidad de ejemplares es obligatoria")
    @Min(value =0, message ="La cantidad de ejemplares no puede ser negativa")
    private Integer ejemplares;

    public Planta() {

    }

    public Planta(Long id, String nombre, Integer ejemplares) {
        this.id = id;
        this.nombre = nombre;
        this.ejemplares = ejemplares;
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

    public Integer getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(Integer ejemplares) {
        this.ejemplares = ejemplares;
    }
}
