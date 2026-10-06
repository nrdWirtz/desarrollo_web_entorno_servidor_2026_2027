package org.example.catalogo.model;

public class TvShow {

    private Integer id;
    private String title;
    private int seasons;

    public TvShow(Integer id, String title, int seasons) {
        this.id = id;
        this.title = title;
        this.seasons = seasons;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSeasons() {
        return seasons;
    }

    public void setSeasons(int seasons) {
        this.seasons = seasons;
    }
}
