package dev.twozer00.projectm.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Trending implements Serializable {
    private static final String BASE_URL_POSTER = "https://image.tmdb.org/t/p/w500" ;
    private static final String BASE_URL_BACKDROP = "https://image.tmdb.org/t/p/w780" ;
    private static final String BASE_URL_PROFILE = "https://image.tmdb.org/t/p/w185" ;
    private boolean adult;
    private String backdrop_path;
    private ArrayList< Integer > genre_ids = new ArrayList<>();
    private float id;
    private String original_language;
    private String original_title;
    private String overview;
    private float popularity;
    private String poster_path;
    private String release_date;
    private String title;
    private boolean video;
    private String media_type;
    private float vote_average;
    private float vote_count;
    ArrayList< Movie > also_known_as = new ArrayList<>();
    private String biography;
    private String birthday;
    private String deathday;
    private float gender;
    private String homepage;
    private String imdb_id;
    private String name;
    private String place_of_birth;
    private String profile_path;
    private String first_air_date;
    ArrayList < String > origin_country = new ArrayList<>();
    private String original_name;


    public Object getMediaObject(){
        Log.d("Trending", "getMediaObject: " + media_type);
        if(media_type != null){
            switch (media_type){
                case "movie":
                    return new Movie(adult,backdrop_path,genre_ids,id,original_language,original_title,overview,popularity,poster_path,release_date,title,video,vote_average,vote_count);
                case "person":
                    return new Person(adult,also_known_as,biography,birthday,deathday,gender,homepage,id,imdb_id,name,place_of_birth,popularity,profile_path);
                case "tv":
                    return new TvShow(poster_path,popularity,id,backdrop_path,vote_average,overview,first_air_date,origin_country,genre_ids,original_language,vote_count,name,original_name);
            }
        }
        else{
            return new Movie(adult,backdrop_path,genre_ids,id,original_language,original_title,overview,popularity,poster_path,release_date,title,video,vote_average,vote_count);
        }
        return null;
    }

    public Trending(boolean adult, String backdrop_path, ArrayList<Integer> genre_ids, float id, String original_language, String original_title, String overview, float popularity, String poster_path, String release_date, String title, boolean video, String media_type, float vote_average, float vote_count, ArrayList<Movie> also_known_as, String biography, String birthday, String deathday, float gender, String homepage, String imdb_id, String name, String place_of_birth, String profile_path, String first_air_date, ArrayList<String> origin_country, String original_name) {
        this.adult = adult; // all
        this.backdrop_path = backdrop_path; //all
        this.genre_ids = genre_ids; // tv,movies
        this.id = id; // all
        this.original_language = original_language; //tv,movies
        this.original_title = original_title;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.title = title;
        this.video = video;
        this.media_type = media_type;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.also_known_as = also_known_as;
        this.biography = biography;
        this.birthday = birthday;
        this.deathday = deathday;
        this.gender = gender;
        this.homepage = homepage;
        this.imdb_id = imdb_id;
        this.name = name;
        this.place_of_birth = place_of_birth;
        this.profile_path = profile_path;
        this.first_air_date = first_air_date;
        this.origin_country = origin_country;
        this.original_name = original_name;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return BASE_URL_BACKDROP + backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
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

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return BASE_URL_POSTER+poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
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

    public ArrayList<Movie> getAlso_known_as() {
        return also_known_as;
    }

    public void setAlso_known_as(ArrayList<Movie> also_known_as) {
        this.also_known_as = also_known_as;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public float getGender() {
        return gender;
    }

    public void setGender(float gender) {
        this.gender = gender;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getProfile_path() {
        return BASE_URL_PROFILE+profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public ArrayList<String> getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(ArrayList<String> origin_country) {
        this.origin_country = origin_country;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }
}
