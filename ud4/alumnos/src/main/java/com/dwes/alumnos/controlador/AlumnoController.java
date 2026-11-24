package com.dwes.alumnos.controlador;

import com.dwes.alumnos.domain.Alumno;
import com.dwes.alumnos.excepcion.AccionNoPermitidaException;
import com.dwes.alumnos.excepcion.NombreUsuarioDuplicadoException;
import com.dwes.alumnos.servicio.AlumnoServicio;
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

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {


    private final AlumnoServicio servicio;

    public AlumnoController(AlumnoServicio servicio) {
        this.servicio = servicio;
    }

    // ---------- Listado y ficha ----------

    @GetMapping
    public String listar(@RequestParam(required = false) String filtroNombre, Model model) {
        model.addAttribute("alumnos", servicio.listar(filtroNombre));
        model.addAttribute("filtroNombre", filtroNombre);
        return "alumnos";
    }

    @GetMapping("/{id}")
    public String ver(@PathVariable Long id, Model model) {
        model.addAttribute("alumno", servicio.buscarPorId(id));
        return "alumno";
    }

    // ---------- Alta ----------

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("alumno", new Alumno());
        return "alumno-form";
    }

    @PostMapping("/nuevo")
    public String guardar(@Valid @ModelAttribute Alumno alumno, BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "alumno-form";
        }
        try {
            servicio.crear(alumno);
        } catch (NombreUsuarioDuplicadoException e) {
            result.rejectValue("nombreUsuario", "usuario.duplicado", e.getMessage());
            return "alumno-form";
        }
        redirectAttributes.addFlashAttribute("aviso", "Alumno creado");
        return "redirect:/alumnos";
    }

    // ---------- Edición ----------

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("alumno", servicio.buscarPorId(id));
        return "alumno-form";
    }

    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute Alumno alumno,
                             BindingResult result, RedirectAttributes redirectAttributes) {
        // El id viene de la ruta; así el formulario, si vuelve a mostrarse, sigue apuntando a /editar
        alumno.setId(id);
        if (result.hasErrors()) {
            return "alumno-form";
        }
        try {
            servicio.actualizar(id, alumno);
        } catch (NombreUsuarioDuplicadoException e) {
            result.rejectValue("nombreUsuario", "usuario.duplicado", e.getMessage());
            return "alumno-form";
        }
        redirectAttributes.addFlashAttribute("aviso", "Alumno actualizado");
        return "redirect:/alumnos";
    }

    // ---------- Activar, desactivar y eliminar ----------

    @PostMapping("/{id}/activar")
    public String activar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Alumno alumno = servicio.activar(id);
            redirectAttributes.addFlashAttribute("aviso",
                    "Alumno " + alumno.getNombre() + " activado");
        } catch (AccionNoPermitidaException e) {
            redirectAttributes.addFlashAttribute("aviso", e.getMessage());
        }
        return "redirect:/alumnos";
    }

    @PostMapping("/{id}/desactivar")
    public String desactivar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Alumno alumno = servicio.desactivar(id);
            redirectAttributes.addFlashAttribute("aviso",
                    "Alumno " + alumno.getNombre() + " desactivado");
        } catch (AccionNoPermitidaException e) {
            redirectAttributes.addFlashAttribute("aviso", e.getMessage());
        }
        return "redirect:/alumnos";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            servicio.eliminar(id);
            redirectAttributes.addFlashAttribute("aviso", "Alumno eliminado");
        } catch (AccionNoPermitidaException e) {
            redirectAttributes.addFlashAttribute("aviso", e.getMessage());
        }
        return "redirect:/alumnos";
    }
}
