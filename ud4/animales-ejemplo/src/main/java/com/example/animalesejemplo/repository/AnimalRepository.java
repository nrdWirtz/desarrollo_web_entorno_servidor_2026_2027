package com.example.animalesejemplo.repository;

import com.example.animalesejemplo.model.Animal;

import java.util.List;

public interface AnimalRepository {

    public List<Animal> findAll();

    public Animal findById(Long id);

    public Animal save(Animal nuevo);

    public Animal findByName(String nombre);
}
