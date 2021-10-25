package dev.twozer00.projectm.model;

import java.util.ArrayList;

public class TvShowCredits {
    private ArrayList<TvShow> cast;
    private ArrayList<TvShow> crew;
    private Integer id;

    public ArrayList<TvShow> getCast() {
        return cast;
    }

    public void setCast(ArrayList<TvShow> cast) {
        this.cast = cast;
    }

    public ArrayList<TvShow> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<TvShow> crew) {
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
