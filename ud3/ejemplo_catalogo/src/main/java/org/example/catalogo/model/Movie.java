package org.example.catalogo.model;

public class Movie {
    private Integer id;
    private String name;
    private String director;
    private int year;

    private String ageRating;


    public Movie(Integer id, String name, String director, int year, String ageRating) {
        this.id = id;
        this.name = name;
        this.director = director;
        this.year = year;
        this.ageRating = ageRating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }
}
