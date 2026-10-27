package com.dwes.todo.model;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

public class Tarea {
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 80, message = "Máximo 80 caracteres")
    private String titulo;

    @NotNull(message = "Elige una prioridad")
    private Prioridad prioridad = Prioridad.MEDIA;

    @NotNull(message = "La fecha límite es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaLimite;

    private boolean completada;

    public Tarea() { }

    public Tarea(String titulo, Prioridad prioridad, LocalDate fechaLimite) {
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.fechaLimite = fechaLimite;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }
    public LocalDate getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
}
