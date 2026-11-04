package com.example.animalesejemplo.mapper;

import com.example.animalesejemplo.dto.AnimalDTO;
import com.example.animalesejemplo.model.Animal;

import java.util.ArrayList;
import java.util.List;

public class AnimalMapper {

    // Convertir de DTO a Entidad de modelo
    public static Animal toEntity(AnimalDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Animal(dto.getId(), dto.getNombre());
    }

    // Convertir de Entidad a DTO
    public static AnimalDTO toDTO(Animal entity) {
        if (entity == null) {
            return null;
        }
        return new AnimalDTO(entity.getId(), entity.getNombre());
    }

    // Convertir de Lista de Entidades a Lista de DTOs
    public static List<AnimalDTO> toDTOList(List<Animal> entityList) {
        if (entityList == null) {
            return null;
        }
        // --- Opción con bucle FOR ---
        List<AnimalDTO> dtoList = new ArrayList<>();
        for (Animal entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;

        /*
         // Opción alternativa con STREAMS  ---
        return entityList.stream()
                .map(AnimalMapper::toDTO)
                .collect(Collectors.toList());
        */
    }
}
