package org.dwes.registro.controller;

import org.dwes.registro.entity.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    List<Usuario> usuarioList = new ArrayList<>();

    @GetMapping("/")
    public String inicio(Model model){
        model.addAttribute("usuario", new Usuario());
        return "index";
    }

    @GetMapping("/editar")
    public String editarUsuario(Model model){
        //variable para controlar la acción en el formulario
        model.addAttribute("editar", true);
        // se pasa un usuario de ejemplo para editar
        model.addAttribute("usuario", new Usuario(1, "Juan", "prueba@prueba.com"));
        return "index";
    }

    @PostMapping("/editar")
    public String editarUsuarioRegistrado(@ModelAttribute Usuario usuario){
    //logica para editar

        // siempre después de un POST se hace una redirección
        return "redirect:/";
    }

    @PostMapping("/registro")
    public String registroUsuario(@ModelAttribute Usuario usuario){
        usuarioList.add(usuario);
        return "redirect:/";
    }
}
