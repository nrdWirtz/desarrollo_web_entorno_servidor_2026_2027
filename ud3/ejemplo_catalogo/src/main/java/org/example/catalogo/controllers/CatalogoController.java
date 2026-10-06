package org.example.catalogo.controllers;

import jakarta.validation.Valid;
import org.example.catalogo.model.Movie;
import org.example.catalogo.model.TvShow;
import org.example.catalogo.model.dtos.TvShowDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CatalogoController {

    private List<Movie> movieList = new ArrayList<>();
    private List<TvShow> tvShowList = new ArrayList<>();


    public CatalogoController(){
        inicializarPeliculas();
        inicializarSeries();
    }
    @GetMapping("/")
    public String paginaInicio(){
        return "index";
    }

    @GetMapping("/movies")
    public String paginaPeliculas(@RequestParam(required = false) String calificacion, Model model){
        model.addAttribute("peliculas", filtrarPeliculas(calificacion));
        return "peliculas";
    }

    @GetMapping("/movies/{id}")
    public String verDetallePelicula(@PathVariable int id, Model model){
        model.addAttribute("pelicula", findMovieById(id));
        return "detallePelicula";
    }

    @GetMapping("/series")
    public String paginaSeries(Model model){
        model.addAttribute("series", tvShowList);
        return "series";
    }

    @PostMapping("/tvshows")
    public String crearSerie(@Valid @ModelAttribute("serieDTO") TvShowDTO serieDTO, BindingResult result) {
        if (result.hasErrors()) { //si hay errores de validación
            return "nuevaSerie"; // vuelve a la página del formulario para mostrar lo errores, no se creaa la nueva serie
        }

        int nuevoId = tvShowList.stream().mapToInt(TvShow::getId).max().orElse(0) + 1;
        tvShowList.add(new TvShow(nuevoId, serieDTO.getTitle(), serieDTO.getSeasons()));

        return "redirect:/series"; // evita reenviar el formulario al recargar
    }

    @GetMapping("/series/nueva")
    public String paginaCrearSerie(Model model) {
        model.addAttribute("serieDTO", new TvShowDTO());
        return "nuevaSerie";
    }

    private void inicializarPeliculas(){
        movieList.add(new Movie(1, "Jurassic Park", "Steven Spielberg", 1993, "+12"));
        movieList.add(new Movie(2, "Star Wars", "George Lucas", 1977, "TP"));
        movieList.add(new Movie(3, "Terminator", "James Cameron", 1984, "+16"));
        movieList.add(new Movie(4, "El Rey León", "Roger Allers", 1994, "TP"));
        movieList.add(new Movie(5, "Toy Story", "John Lasseter", 1995, "TP"));
        movieList.add(new Movie(6, "Pulp Fiction", "Quentin Tarantino", 1994, "+18"));
        movieList.add(new Movie(7, "Matrix", "Lana y Lilly Wachowski", 1999, "+16"));
        movieList.add(new Movie(8, "Gladiator", "Ridley Scott", 2000, "+16"));
        movieList.add(new Movie(9, "Titanic", "James Cameron", 1997, "+12"));
        movieList.add(new Movie(10, "Regreso al futuro", "Robert Zemeckis", 1985, "+7"));
        movieList.add(new Movie(11, "Shrek", "Andrew Adamson", 2001, "TP"));
        movieList.add(new Movie(12, "El Padrino", "Francis Ford Coppola", 1972, "+18"));
    }

    private void inicializarSeries(){
        tvShowList.add(new TvShow(1, "La que se avecina", 15));
        tvShowList.add(new TvShow(2, "The Simpsons", 34));
    }

    private Movie findMovieById(int id){
        Movie movie = null;

        for (Movie p : movieList) {
            if (p.getId() == id) {
                movie = p;
                break;
            }
        }
        return movie;
    }

    private List<Movie> filtrarPeliculas(String calificacion) {
        return movieList.stream()
                .filter(m -> calificacion == null || calificacion.isBlank()
                        || m.getAgeRating().equalsIgnoreCase(calificacion))
                .collect(Collectors.toList());
    }
}
