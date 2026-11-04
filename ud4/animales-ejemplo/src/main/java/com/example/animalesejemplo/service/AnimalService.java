package com.example.animalesejemplo.service;

import com.example.animalesejemplo.dto.AnimalDTO;

import java.util.List;

public interface AnimalService {

    public List<AnimalDTO> obtenerAnimales();

    public AnimalDTO crear(AnimalDTO nuevoAnimal);

    public AnimalDTO consultarAnimal(Long id);
}
