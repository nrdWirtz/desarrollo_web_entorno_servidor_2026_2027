package com.example.huerto.repository;

import com.example.huerto.model.Planta;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PlantaRepositoryImpl implements PlantaRepository{

    List<Planta> plantaList =new ArrayList<>();

    Long sequenceId =0L;
    @Override
    public List<Planta> findAll() {
        return plantaList;
    }

    @Override
    public Planta save(Planta planta) {
        if(planta.getId() == null) {
            planta.setId(++sequenceId);
            plantaList.add(planta);
            return planta;
        }

        for(int i=0; i<= plantaList.size(); i++) {
            if(plantaList.get(i).getId().equals(planta.getId())) {
                plantaList.set(i, planta);
                return planta;
            }
        }
        plantaList.add(planta);
        return planta;
    }

    @Override
    public Planta findById(Long id) {
        return plantaList.stream().filter(p-> p.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Planta findByName(String name) {
        return  plantaList.stream().filter(p-> p.getNombre().equals(name)).findFirst().orElse(null);
    }

}
