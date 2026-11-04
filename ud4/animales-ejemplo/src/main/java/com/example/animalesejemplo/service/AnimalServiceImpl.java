package com.example.animalesejemplo.service;

import com.example.animalesejemplo.dto.AnimalDTO;
import com.example.animalesejemplo.exception.ResourceNotFoundException;
import com.example.animalesejemplo.exception.ValidarDatosException;
import com.example.animalesejemplo.mapper.AnimalMapper;
import com.example.animalesejemplo.model.Animal;
import com.example.animalesejemplo.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AnimalServiceImpl implements AnimalService {

    AnimalRepository animalRepository;

    //se inyecta el repository por constructor
    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    //obtener animales
    @Override
    public List<AnimalDTO> obtenerAnimales() {
        return AnimalMapper.toDTOList(animalRepository.findAll());
    }


    // creamos el animal si su nombre no está en nuestro almacén de datos
    @Override
    public AnimalDTO crear(AnimalDTO nuevoAnimalDTO) {
        Animal nuevoAnimal =  AnimalMapper.toEntity(nuevoAnimalDTO);
        validarNombreAnimal(nuevoAnimal);
        return AnimalMapper.toDTO(animalRepository.save(nuevoAnimal));

    }

    @Override
    public AnimalDTO consultarAnimal(Long id) {
        Animal animal = animalRepository.findById(id);
        if(animal== null) {
            throw new ResourceNotFoundException("El animal buscado con id: " + id + ", no existe");
        }
        return AnimalMapper.toDTO(animal);
    }


    // comprobar que no se repite el nombre
    private void validarNombreAnimal(Animal animal) {
        Animal animalBBDD = animalRepository.findByName(animal.getNombre());
        if(animalBBDD != null) {
            // si exite un animal con ese nombre lanzamos una excepción para comunicarlo al controlador
           throw new ValidarDatosException("Error, ya existe un animal con ese nombre");
        }
    }
}
