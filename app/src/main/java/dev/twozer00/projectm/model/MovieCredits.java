package dev.twozer00.projectm.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieCredits implements Serializable {
    private ArrayList<Movie> cast;
    private ArrayList<Movie> crew;
    private Integer id;

    public ArrayList<Movie> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Movie> cast) {
        this.cast = cast;
    }

    public ArrayList<Movie> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<Movie> crew) {
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
