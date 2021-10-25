package dev.twozer00.projectm.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Credits implements Serializable {
    private ArrayList< Cast > cast = new ArrayList<>();
    private ArrayList < Crew > crew = new ArrayList<>();
    private float id;

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }

    public ArrayList<Crew> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<Crew> crew) {
        this.crew = crew;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }
}
