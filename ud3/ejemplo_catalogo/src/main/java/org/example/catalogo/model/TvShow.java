package org.example.catalogo.model;

public class TvShow {
    private String title;
    private int seasons;

    public TvShow(String title, int seasons) {

        this.title = title;
        this.seasons = seasons;
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
