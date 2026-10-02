package org.example.catalogo.controllers;

import org.example.catalogo.model.Movie;
import org.example.catalogo.model.TvShow;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

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
    public String paginaPeliculas(Model model){
        model.addAttribute("peliculas", movieList);
        return "peliculas";
    }

    @GetMapping("/series")
    public String paginaSeries(Model model){
        model.addAttribute("series", tvShowList);
        return "series";
    }

    private void inicializarPeliculas(){
        movieList.add(new Movie("Resident Evil","Héctor", 2026));
        movieList.add(new Movie("La vida de Brian", "Guillermo", 1979));
        movieList.add(new Movie("El show de Truman", "Dani", 1960));
    }

    private void inicializarSeries(){
        tvShowList.add(new TvShow("La que se avecina", 15));
        tvShowList.add(new TvShow("The Simpsons", 34));
    }
}
