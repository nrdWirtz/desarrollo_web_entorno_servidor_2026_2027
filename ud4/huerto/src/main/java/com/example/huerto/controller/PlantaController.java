package com.example.huerto.controller;


import com.example.huerto.exception.ValidationCustomException;
import com.example.huerto.model.Planta;
import com.example.huerto.service.PlantaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/planta")
public class PlantaController {

    private PlantaService plantaService;

    public PlantaController(PlantaService plantaService) {
        this.plantaService = plantaService;
    }

    @GetMapping()
    public String lista(Model model) {
        model.addAttribute("plantas", plantaService.obtenerPlantas());
        return "planta";
    }

    @GetMapping("/nuevo")
    public  String mostrarFormularioNuevo(Model model) {
        model.addAttribute("plantaDTO", new Planta());
        return "formularioAlta";
    }

    @PostMapping("/nuevo")
    public  String crearNuevo(@Valid @ModelAttribute("plantaDTO") Planta planta, BindingResult errors, RedirectAttributes flash) {
        try {
            if(errors.hasErrors()) {
                return "formularioAlta";
            }
            plantaService.crearNueva(planta);
            flash.addFlashAttribute("success", "Se ha dado de alta una nueva planta");

        } catch (ValidationCustomException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/planta";
    }

    @PostMapping("/{id}/cosechar")
    public  String cosecharPlanta(@PathVariable Long id, RedirectAttributes flash) {
            try {
                Planta planta = plantaService.cosechar(id);
                flash.addFlashAttribute("success", "Se ha cosechado con exito la " + planta.getNombre());
            } catch (ValidationCustomException ex) {
                flash.addFlashAttribute("error", ex.getMessage());
            }

        return "redirect:/planta";
    }

}
