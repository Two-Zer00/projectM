package dev.twozer00.projectm.model;

import java.util.ArrayList;

public class Person {

    private boolean adult;
    ArrayList< Movie > also_known_as = new ArrayList<>();
    private String biography = null;
    private String birthday = null;
    private String deathday = null;
    private float gender;
    private String homepage = null;
    private float id;
    private String imdb_id = null;
    private String name;
    private String place_of_birth = null;
    private float popularity;
    private String profile_path = null;

    public Person() {
    }

    public Person(boolean adult, ArrayList<Movie> also_known_as, String biography, String birthday, String deathday, float gender, String homepage, float id, String imdb_id, String name, String place_of_birth, float popularity, String profile_path) {
        this.adult = adult;
        this.also_known_as = also_known_as;
        this.biography = biography;
        this.birthday = birthday;
        this.deathday = deathday;
        this.gender = gender;
        this.homepage = homepage;
        this.id = id;
        this.imdb_id = imdb_id;
        this.name = name;
        this.place_of_birth = place_of_birth;
        this.popularity = popularity;
        this.profile_path = profile_path;
    }

    // Getter Methods

    public boolean getAdult() {
        return adult;
    }

    public String getBiography() {
        return biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public float getGender() {
        return gender;
    }

    public String getHomepage() {
        return homepage;
    }

    public float getId() {
        return id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getName() {
        return name;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getProfile_path() {
        return profile_path;
    }

    // Setter Methods

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public void setGender(float gender) {
        this.gender = gender;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
