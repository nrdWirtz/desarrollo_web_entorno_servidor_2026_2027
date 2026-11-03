package com.example.demo.repository;

import com.example.demo.model.Animal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AnimalRepository {

    private List<Animal> animalList = new ArrayList<>();
    private Long ultimoId = 0L;

    // buscar todos los animales

    public List<Animal> findAll() {
        return animalList;
    }

    // buscar por id

    public Animal findById(Long id) {
        return animalList.stream()
                .filter(animal -> animal.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Animal save(Animal nuevo) {
        // el animal es nuevo no tiene id
        if(nuevo.getId() == null) {
            nuevo.setId(++ultimoId);
            animalList.add(nuevo);
            return nuevo;
        }

        // tiene id los buscamos en la lista para actualizar valores
        for(int i=0; i<animalList.size(); i++) {
            if(animalList.get(i).getId().equals(nuevo.getId())) {
                animalList.set(i, nuevo);
                return nuevo;
            }
        }
        // caso poco probable que tenga id pero que no esté en la lista
        animalList.add(nuevo);

        return nuevo;
    }

    public Animal findByName(String nombre) {
        return animalList.stream()
                .filter(animal -> animal.getNombre().equals(nombre))
                .findFirst().orElse(null);
    }


}
