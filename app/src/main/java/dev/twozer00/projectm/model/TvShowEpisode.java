package dev.twozer00.projectm.model;

import java.io.Serializable;
import java.util.ArrayList;

public class TvShowEpisode implements Serializable {
    private String air_date;
    ArrayList< Object > crew = new ArrayList < Object > ();
    private float episode_number;
    ArrayList < Object > guest_stars = new ArrayList < Object > ();
    private String name;
    private String overview;
    private float id;
    private String production_code;
    private float season_number;
    private String still_path;
    private float vote_average;
    private float vote_count;

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public ArrayList<Object> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<Object> crew) {
        this.crew = crew;
    }

    public float getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(float episode_number) {
        this.episode_number = episode_number;
    }

    public ArrayList<Object> getGuest_stars() {
        return guest_stars;
    }

    public void setGuest_stars(ArrayList<Object> guest_stars) {
        this.guest_stars = guest_stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getProduction_code() {
        return production_code;
    }

    public void setProduction_code(String production_code) {
        this.production_code = production_code;
    }

    public float getSeason_number() {
        return season_number;
    }

    public void setSeason_number(float season_number) {
        this.season_number = season_number;
    }

    public String getStill_path() {
        return still_path;
    }

    public void setStill_path(String still_path) {
        this.still_path = still_path;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public float getVote_count() {
        return vote_count;
    }

    public void setVote_count(float vote_count) {
        this.vote_count = vote_count;
    }

    @Override
    public String toString() {
        return "TvShowEpisode{" +
                "air_date='" + air_date + '\'' +
                ", crew=" + crew +
                ", episode_number=" + episode_number +
                ", guest_stars=" + guest_stars +
                ", name='" + name + '\'' +
                ", overview='" + overview + '\'' +
                ", id=" + id +
                ", production_code='" + production_code + '\'' +
                ", season_number=" + season_number +
                ", still_path='" + still_path + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                '}';
    }
}
