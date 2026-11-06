package com.example.huerto.service;

import com.example.huerto.model.Planta;

import java.util.List;

public interface PlantaService {

    List<Planta> obtenerPlantas();

    Planta crearNueva(Planta planta);

    Planta cosechar(Long id);
}
