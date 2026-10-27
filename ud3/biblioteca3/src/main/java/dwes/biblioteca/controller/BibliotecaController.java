package dwes.biblioteca.controller;

import dwes.biblioteca.model.Libro;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BibliotecaController {

    // Esta lista se comparte entre todas las peticiones y vive mientras la aplicación está arrancada.
    private final List<Libro> libros = new ArrayList<>();

    public BibliotecaController() {
        libros.add(new Libro(1, "El principito", "Antoine de Saint-Exupéry", 96, false));
        libros.add(new Libro(2, "Momo", "Michael Ende", 304, false));
        libros.add(new Libro(3, "La isla del tesoro", "Robert Louis Stevenson", 240, true));
        libros.add(new Libro(4, "Matilda", "Roald Dahl", 240, false));
        libros.add(new Libro(5, "Frankenstein", "Mary Shelley", 280, false));
        libros.add(new Libro(6, "El hobbit", "J. R. R. Tolkien", 320, true));
        libros.add(new Libro(7, "Orgullo y prejuicio", "Jane Austen", 448, false));
        libros.add(new Libro(8, "La historia interminable", "Michael Ende", 496, false));
        libros.add(new Libro(9, "Rebelión en la granja", "George Orwell", 144, false));
        libros.add(new Libro(10, "Nada", "Carmen Laforet", 304, true));
        libros.add(new Libro(11, "La sombra del viento", "Carlos Ruiz Zafón", 576, false));
        libros.add(new Libro(12, "Mujercitas", "Louisa May Alcott", 768, false));
        libros.add(new Libro(13, "El camino", "Miguel Delibes", 224, false));
        libros.add(new Libro(14, "Los juegos del hambre", "Suzanne Collins", 400, true));
        libros.add(new Libro(15, "Las aventuras de Sherlock Holmes", "Arthur Conan Doyle", 352, false));
        libros.add(new Libro(16, "La casa de los espíritus", "Isabel Allende", 480, false));
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/listado";
    }

    @GetMapping("/listado")
    public String mostrarListado(@RequestParam(required = false) Boolean prestado, Model model) {
        model.addAttribute("libros", filtrarLibros(prestado));
        model.addAttribute("prestado", prestado);
        return "listado";
    }

    @GetMapping("/libro/{id}")
    public String mostrarDetalle(@PathVariable long id, Model model) {
        //TODO tratar caso libro no existe
        model.addAttribute("libro", buscarPorId(id));
        return "detalle";
    }

    @PostMapping("/libro/toggle/{id}")
    public String cambiarEstado(@PathVariable long id) {
        //TODO tratar caso libro no existe
        Libro libro = buscarPorId(id);
        // Se cambia el estado real del libro
        libro.setPrestado(!libro.isPrestado());

        return "redirect:/listado";
    }

    private List<Libro> filtrarLibros(Boolean prestado) {
        List<Libro> resultado = new ArrayList<>();
        for (Libro libro : libros) {
            // Se comprueba null antes de comparar para evitar el desempaquetado de un Boolean nulo.
            if (prestado == null || libro.isPrestado() == prestado) {
                resultado.add(libro);
            }
        }
        return resultado;
    }

    private Libro buscarPorId(long id) {
        // El identificador de un libro no es su posición dentro de la lista.
        for (Libro libro : libros) {
            if (libro.getId() == id) {
                return libro;
            }
        }
        return null;
    }
}
