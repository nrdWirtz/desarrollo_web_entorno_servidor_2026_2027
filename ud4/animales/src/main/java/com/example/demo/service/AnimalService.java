package com.example.demo.service;

import com.example.demo.model.Animal;
import com.example.demo.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    //obtener animales
    public List<Animal> obtenerAnimales() {
        return animalRepository.findAll();
    }

    // creamos el animal si su nombre no está en nuestro almacén de datos
    public Animal crear(Animal nuevoAnimal) {
        if(validarNombreAnimal(nuevoAnimal)) {
            Animal animal = animalRepository.save(nuevoAnimal);
            return animal;
        }
        return null;

    }

    // comprobar que no se repite el nombre
    private boolean validarNombreAnimal(Animal animal) {
        Animal animalBBDD = animalRepository.findByName(animal.getNombre());
        if(animalBBDD != null) {
            // si exite un animal con ese nombre
            // habrá que comunicarlo de alguna forma
            return false;
        }
        return true;
    }
}
