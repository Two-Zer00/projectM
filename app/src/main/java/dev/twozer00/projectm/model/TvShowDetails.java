package dev.twozer00.projectm.model;

import java.io.Serializable;
import java.util.ArrayList;

public class TvShowDetails implements Serializable {
    private String backdrop_path;
    ArrayList< Object > created_by = new ArrayList < Object > ();
    ArrayList <Integer> episode_run_time = new ArrayList<>();
    private String first_air_date;
    ArrayList < Genre > genres = new ArrayList<>();
    private String homepage;
    private float id;
    private boolean in_production;
    ArrayList < Object > languages = new ArrayList < Object > ();
    private String last_air_date;
    TvShowEpisode last_episode_to_air;
    private String name;
    private TvShowEpisode next_episode_to_air;
    ArrayList < Object > networks = new ArrayList < Object > ();
    private float number_of_episodes;
    private float number_of_seasons;
    ArrayList < Object > origin_country = new ArrayList < Object > ();
    private String original_language;
    private String original_name;
    private String overview;
    private float popularity;
    private String poster_path;
    ArrayList < Object > production_companies = new ArrayList < Object > ();
    ArrayList < Object > production_countries = new ArrayList < Object > ();
    ArrayList < Object > seasons = new ArrayList < Object > ();
    ArrayList < Object > spoken_languages = new ArrayList < Object > ();
    private String status;
    private String tagline;
    private String type;
    private float vote_average;
    private float vote_count;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public ArrayList<Object> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(ArrayList<Object> created_by) {
        this.created_by = created_by;
    }

    public ArrayList<Integer> getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(ArrayList<Integer> episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public boolean isIn_production() {
        return in_production;
    }

    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }

    public ArrayList<Object> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<Object> languages) {
        this.languages = languages;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public TvShowEpisode getLast_episode_to_air() {
        return last_episode_to_air;
    }

    public void setLast_episode_to_air(TvShowEpisode last_episode_to_air) {
        this.last_episode_to_air = last_episode_to_air;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TvShowEpisode getNext_episode_to_air() {
        return next_episode_to_air;
    }

    public void setNext_episode_to_air(TvShowEpisode next_episode_to_air) {
        this.next_episode_to_air = next_episode_to_air;
    }

    public ArrayList<Object> getNetworks() {
        return networks;
    }

    public void setNetworks(ArrayList<Object> networks) {
        this.networks = networks;
    }

    public float getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(float number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public float getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(float number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public ArrayList<Object> getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(ArrayList<Object> origin_country) {
        this.origin_country = origin_country;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    @Override
    public String toString() {
        return "TvShowDetails{" +
                "backdrop_path='" + backdrop_path + '\'' +
                ", created_by=" + created_by +
                ", episode_run_time=" + episode_run_time +
                ", first_air_date='" + first_air_date + '\'' +
                ", genres=" + genres +
                ", homepage='" + homepage + '\'' +
                ", id=" + id +
                ", in_production=" + in_production +
                ", languages=" + languages +
                ", last_air_date='" + last_air_date + '\'' +
                ", last_episode_to_air=" + last_episode_to_air +
                ", name='" + name + '\'' +
                ", next_episode_to_air=" + next_episode_to_air +
                ", networks=" + networks +
                ", number_of_episodes=" + number_of_episodes +
                ", number_of_seasons=" + number_of_seasons +
                ", origin_country=" + origin_country +
                ", original_language='" + original_language + '\'' +
                ", original_name='" + original_name + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", poster_path='" + poster_path + '\'' +
                ", production_companies=" + production_companies +
                ", production_countries=" + production_countries +
                ", seasons=" + seasons +
                ", spoken_languages=" + spoken_languages +
                ", status='" + status + '\'' +
                ", tagline='" + tagline + '\'' +
                ", type='" + type + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                '}';
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public ArrayList<Object> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(ArrayList<Object> production_companies) {
        this.production_companies = production_companies;
    }

    public ArrayList<Object> getProduction_countries() {
        return production_countries;
    }

    public void setProduction_countries(ArrayList<Object> production_countries) {
        this.production_countries = production_countries;
    }

    public ArrayList<Object> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Object> seasons) {
        this.seasons = seasons;
    }

    public ArrayList<Object> getSpoken_languages() {
        return spoken_languages;
    }

    public void setSpoken_languages(ArrayList<Object> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
