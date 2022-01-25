package dev.twozer00.projectm.model;

import dev.twozer00.projectm.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;

import static dev.twozer00.projectm.utils.Constants.BASE_URL_IMG;
import static dev.twozer00.projectm.utils.Constants.BASE_URL_IMG_BACKDROP;

public class TvShow implements Serializable {
    private String poster_path;
    private float popularity;
    private float id;
    private String backdrop_path;
    private float vote_average;
    private String overview;
    private String first_air_date;
    ArrayList < String > origin_country = new ArrayList<>();
    ArrayList< Integer > genre_ids = new ArrayList<>();
    private String original_language;
    private float vote_count;
    private String name;
    private String original_name;

    public TvShow() {
    }

    public TvShow(String poster_path, float popularity, float id, String backdrop_path, float vote_average, String overview, String first_air_date, ArrayList<String> origin_country, ArrayList<Integer> genre_ids, String original_language, float vote_count, String name, String original_name) {
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.id = id;
        this.backdrop_path = backdrop_path;
        this.vote_average = vote_average;
        this.overview = overview;
        this.first_air_date = first_air_date;
        this.origin_country = origin_country;
        this.genre_ids = genre_ids;
        this.original_language = original_language;
        this.vote_count = vote_count;
        this.name = name;
        this.original_name = original_name;
    }


// Getter Methods

    public String getPoster_path() {
        return BASE_URL_IMG+poster_path;
    }

    public float getPopularity() {
        return popularity;
    }

    public float getId() {
        return id;
    }

    public String getBackdrop_path() {
        return BASE_URL_IMG_BACKDROP +backdrop_path;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public float getVote_count() {
        return vote_count;
    }

    public String getName() {
        return name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    // Setter Methods

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setVote_count(float vote_count) {
        this.vote_count = vote_count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    @Override
    public String toString() {
        return "TvShow{" +
                "poster_path='" + poster_path + '\'' +
                ", popularity=" + popularity +
                ", id=" + id +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", vote_average=" + vote_average +
                ", overview='" + overview + '\'' +
                ", first_air_date='" + first_air_date + '\'' +
                ", origin_country=" + origin_country +
                ", genre_ids=" + genre_ids +
                ", original_language='" + original_language + '\'' +
                ", vote_count=" + vote_count +
                ", name='" + name + '\'' +
                ", original_name='" + original_name + '\'' +
                '}';
    }
}
