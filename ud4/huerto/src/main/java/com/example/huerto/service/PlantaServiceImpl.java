package com.example.huerto.service;

import com.example.huerto.exception.ResourceNotFoundException;
import com.example.huerto.exception.ValidationCustomException;
import com.example.huerto.model.Planta;
import com.example.huerto.repository.PlantaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantaServiceImpl implements PlantaService{

    private PlantaRepository plantaRepository;

    public PlantaServiceImpl(PlantaRepository plantaRepository) {
        this.plantaRepository = plantaRepository;
    }

    @Override
    public List<Planta> obtenerPlantas() {
        return plantaRepository.findAll();
    }

    @Override
    public Planta crearNueva(Planta planta) {
        validarNombre(planta);
        return plantaRepository.save(planta);
    }

    @Override
    public Planta cosechar(Long id) {
        Planta plantaBBDD = plantaRepository.findById(id);
        if(plantaBBDD == null) {
            throw new ResourceNotFoundException("No se ha encontrado el recurso solicitado");
        }
        validarNumeroEjemplares(plantaBBDD);

        plantaBBDD.setEjemplares(plantaBBDD.getEjemplares() -1);
        plantaRepository.save(plantaBBDD);

        return plantaBBDD;
    }


    private void validarNombre(Planta planta) {
        if(plantaRepository.findByName(planta.getNombre()) != null) {
            throw new ValidationCustomException("Nombre de planta repetido");
        }

    }

    private void validarNumeroEjemplares(Planta planta) {
        if(planta.getEjemplares() == 0)  {
            throw new ValidationCustomException("No se puede cosechar no se dispone de ejemplares suficientes");
        }
    }
}
