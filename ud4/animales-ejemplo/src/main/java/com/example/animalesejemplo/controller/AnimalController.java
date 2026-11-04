package com.example.animalesejemplo.controller;

import com.example.animalesejemplo.dto.AnimalDTO;
import com.example.animalesejemplo.exception.ValidarDatosException;
import com.example.animalesejemplo.service.AnimalService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


// todo lo que entre por este controlador empieza por  /animal
@Controller
@RequestMapping("/animal")
public class AnimalController {

    AnimalService animalService;

    // inyectamos el servicio por constructor
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    // pasamos información a la vista mediante model
    // va a mostrar el listado de animales
    // y un formulario para crear nuevos animales
    @GetMapping()
    public String animalesPrincipal(Model model) {
        // pasamos el listado de animales
        model.addAttribute("animalesLista", animalService.obtenerAnimales());
        // preparamos el objeto del formulario
        model.addAttribute("nuevoAnimal", new AnimalDTO());
        // devolvemo la vista
        return "home";


    }

    // recibimos los datos del formulario
    //@Valid aplica las validaciones que hemos definido en la clase Animal
    // BindingResult no enlaza con el html los errores del formulario
    // Model lo utilizamos para pasar datos a la vista
    // RedirectAttributes lo utilizamos para pasar datos en la redirección
    @PostMapping("nuevo")
    public String guardarAnimal(@Valid @ModelAttribute("nuevoAnimal") AnimalDTO animalFormulario,
                                BindingResult errores, Model model,
                                RedirectAttributes flash){
        // comprobamos que no hay errores si los hay volvemos al formulario
        if(errores.hasErrors()) {
            model.addAttribute("animalesLista", animalService.obtenerAnimales());
            return "home";
        }
        try {
            // llamamos al servicio para crear el animal
            AnimalDTO animal = animalService.crear(animalFormulario);

            // si se crea correctamente mandamos un mensaje al usuario comunicando que toodo OK
            flash.addFlashAttribute("success",
                    "Se ha creado el animal " + animal.getNombre());
            // si se guarda el animal se aplica redirección para no reenviar el formulario
            return "redirect:/animal";
        } catch (ValidarDatosException ex) {
            // si no se guarda el nuevo elemento indicamos el motivo y volvemos a la página
            flash.addFlashAttribute("error", ex.getMessage());

            return "redirect:/animal";
        }

    }

    //consultamos un animal en concreto
    @GetMapping("/{id}")
    public String obtenerDetalleAnimal(@PathVariable Long id, Model model) {
        // en este caso no estamos capturando ninguna excepción
        // si el id no existe y se lanzar la excepción la controla la clase GlobalManagerExcepcion
        model.addAttribute("animal", animalService.consultarAnimal(id));
        return "animal";
    }


}
