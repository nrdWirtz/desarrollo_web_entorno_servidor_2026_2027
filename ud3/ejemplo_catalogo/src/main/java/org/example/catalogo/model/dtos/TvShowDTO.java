package org.example.catalogo.model.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

// @user dnr
public class TvShowDTO {
    @NotBlank(message = "El título es obligatorio")
    private String title;

    @Min(value = 1, message = "Debe tener al menos 1 temporada")
    @Max(value = 100, message = "Número de temporadas no válido")
    private int seasons;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getSeasons() { return seasons; }
    public void setSeasons(int seasons) { this.seasons = seasons; }
}
