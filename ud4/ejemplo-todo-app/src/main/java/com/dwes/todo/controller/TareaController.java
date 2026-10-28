package com.dwes.todo.controller;

import com.dwes.todo.model.Tarea;
import com.dwes.todo.model.Prioridad;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.dwes.todo.repository.TareaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador de Spring para gestionar las operaciones de la lista de tareas.
 */
@Controller
@RequestMapping("/tareas")
public class TareaController {

    TareaRepository tareaRepository;

    public TareaController(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    private static final int MAX_ALTAS_PENDIENTES = 3;



    /**
     * Atiende peticiones GET a "/tareas" para mostrar la lista de tareas.
     */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tareas", tareaRepository.findAll());
        return "tareas/lista";
    }

    /**
     * Atiende peticiones GET a "/tareas/nueva" para mostrar el formulario de creación.
     */
    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("tarea", new Tarea());
        model.addAttribute("prioridades", Prioridad.values());
        return "tareas/formulario";
    }

    /**
     * Atiende peticiones POST a "/tareas" para validar y guardar una nueva tarea.
     */
    @PostMapping
    public String crear(@Valid @ModelAttribute("tarea") Tarea datos,
                        BindingResult errores, Model model, RedirectAttributes flash) {
        if (errores.hasErrors()) {
            model.addAttribute("prioridades", Prioridad.values());
            return "tareas/formulario";
        }
        Tarea nueva = new Tarea(datos.getTitulo().trim(),
                datos.getPrioridad(), datos.getFechaLimite());
        if (!validarNueva(nueva, errores)) {
            model.addAttribute("prioridades", Prioridad.values());
            return "tareas/formulario";
        }
        tareaRepository.save(nueva);
        flash.addFlashAttribute("mensaje", "Tarea creada: " + nueva.getTitulo());
        return "redirect:/tareas";
    }

    /**
     * Atiende peticiones POST a "/tareas/{id}/completar" para marcar una tarea como hecha.
     */
    @PostMapping("/{id}/completar")
    public String completar(@PathVariable("id") Long id,
                            RedirectAttributes flash) {
        Tarea tarea = tareaRepository.findById(id);
        if (tarea == null) {
            flash.addFlashAttribute("error", "No existe la tarea con id " + id);
        } else if (tarea.isCompletada()) {
            flash.addFlashAttribute("error", "La tarea ya estaba completada");
        } else {
            tarea.setCompletada(true);
            tareaRepository.save(tarea);
            flash.addFlashAttribute("mensaje", "Tarea completada");
        }
        return "redirect:/tareas";
    }

    /**
     * Atiende peticiones POST a "/tareas/{id}/eliminar" para borrar una tarea de la lista.
     */
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable("id") Long id,
                           RedirectAttributes flash) {
        Tarea tarea = tareaRepository.findById(id);
        if (tarea == null) {
            flash.addFlashAttribute("error", "No existe la tarea con id " + id);
        } else {
            tareaRepository.deleteById(id);
            flash.addFlashAttribute("mensaje", "Tarea eliminada");
        }
        return "redirect:/tareas";
    }

    /**
     * Método interno para validar las reglas de negocio al crear una tarea.
     */
    private boolean validarNueva(Tarea nueva, BindingResult errores) {
        if (nueva.getFechaLimite().isBefore(LocalDate.now())) {
            errores.reject("regla.negocio",
                    "La fecha límite no puede ser anterior a hoy");
            return false;
        }

        int altasPendientes = 0;
        for (Tarea existente : tareaRepository.findAll()) {
            if (!existente.isCompletada()) {
                if (existente.getTitulo().equalsIgnoreCase(nueva.getTitulo())) {
                    errores.reject("regla.negocio",
                            "Ya existe una tarea pendiente con ese título");
                    return false;
                }
                if (existente.getPrioridad() == Prioridad.ALTA) {
                    altasPendientes++;
                }
            }
        }

        if (nueva.getPrioridad() == Prioridad.ALTA
                && altasPendientes >= MAX_ALTAS_PENDIENTES) {
            errores.reject("regla.negocio",
                    "Solo puede haber tres tareas pendientes de prioridad alta");
            return false;
        }
        return true;
    }



}
