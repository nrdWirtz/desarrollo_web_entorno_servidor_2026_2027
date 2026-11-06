package com.dwes.empleados.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dwes.empleados.exception.ReglaNegocioException;
import com.dwes.empleados.model.Empleado;
import com.dwes.empleados.service.EmpleadoService;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public String listar(@RequestParam(defaultValue = "") String busqueda, Model model) {
        model.addAttribute("empleados", empleadoService.listar(busqueda));
        model.addAttribute("busqueda", busqueda);
        return "empleados/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("edicion", false);
        return "empleados/formulario";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("empleado") Empleado empleado,
                        BindingResult bindingResult, Model model,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("edicion", false);
            return "empleados/formulario";
        }

        try {
            empleadoService.crear(empleado);
        } catch (ReglaNegocioException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("edicion", false);
            return "empleados/formulario";
        }

        redirectAttributes.addFlashAttribute("exito", "Empleado creado correctamente");
        return "redirect:/empleados";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("empleado", empleadoService.buscarPorId(id));
        model.addAttribute("edicion", true);
        return "empleados/formulario";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("empleado") Empleado empleado,
                             BindingResult bindingResult, Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("edicion", true);
            return "empleados/formulario";
        }

        try {
            empleadoService.actualizar(id, empleado);
        } catch (ReglaNegocioException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("edicion", true);
            return "empleados/formulario";
        }

        redirectAttributes.addFlashAttribute("exito", "Empleado modificado correctamente");
        return "redirect:/empleados";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        empleadoService.eliminar(id);
        redirectAttributes.addFlashAttribute("exito", "Empleado borrado correctamente");
        return "redirect:/empleados";
    }

}
