package com.example.huerto.repository;

import com.example.huerto.model.Planta;

import java.util.List;

public interface PlantaRepository {

    List<Planta> findAll();

    Planta save(Planta planta);

    Planta findById(Long id);

    Planta findByName(String name);

}
