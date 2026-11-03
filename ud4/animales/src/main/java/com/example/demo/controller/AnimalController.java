package com.example.demo.controller;

import com.example.demo.model.Animal;
import com.example.demo.service.AnimalService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
// todo lo que entre por este controlador empieza por  /animal
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
        model.addAttribute("nuevoAnimal", new Animal());
        // devolvemo la vista
        return "home";


    }

    // recibimos los datos del formulario
    //@Valid aplica las validaciones que hemos definido en la clase Animal
    // BindingResult no enlaza con el html los errores del formulario
    // Model lo utilizamos para pasar datos a la vista
    // RedirectAttributes lo utilizamos para pasar datos en la redirección
    @PostMapping("nuevo")
    public String guardarAnimal(@Valid @ModelAttribute("nuevoAnimal") Animal animalFormulario,
                                BindingResult errores, Model model,
                                RedirectAttributes flash){
        // comprobamos que no hay errores si los hay volvemos al formulario
        if(errores.hasErrors()) {
            model.addAttribute("animalesLista", animalService.obtenerAnimales());
            return "home";
        }
        // llamamos al servicio para crear el animal
        Animal animal = animalService.crear(animalFormulario);

        // si se crea correctamente mandamos un mensaje al usuario comunicando que toodo OK
        flash.addFlashAttribute("mensaje",
                "Se ha creado el animal "
                        + animal.getNombre() +
                        " con id: "+ animal.getId());

        // si se guarda el animal se aplica redirección para no reenviar el formulario
        return "redirect:/animal";

    }
}
