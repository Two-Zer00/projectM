package dev.twozer00.projectm.model;

import java.io.Serializable;
import java.util.ArrayList;

public class People implements Serializable {
    private String birthday;
    private String known_for_department;
    private String deathday = null;
    private float id;
    private String name;
    ArrayList< String > also_known_as = new ArrayList<>();
    private float gender;
    private String biography;
    private float popularity;
    private String place_of_birth;
    private String profile_path;
    private boolean adult;
    private String imdb_id;
    private String homepage = null;

    // Getter Methods

    public String getBirthday() {
        return birthday;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public String getDeathday() {
        return deathday;
    }

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getGender() {
        return gender;
    }

    public String getBiography() {
        return biography;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public boolean getAdult() {
        return adult;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getHomepage() {
        return homepage;
    }

    // Setter Methods

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(float gender) {
        this.gender = gender;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
}
