package dev.twozer00.projectm.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import static dev.twozer00.projectm.utils.Constants.*;

public class Combined implements Serializable {
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
    ArrayList< String > also_known_as = new ArrayList<>();
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
    private String known_for_department;
    private String original_name;

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

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
        return null;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getBackdrop_path() {
        return BASE_URL_IMG_BACKDROP + backdrop_path;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public float getId() {
        return id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return BASE_URL_IMG+poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public String getMedia_type() {
        return media_type;
    }

    public float getVote_average() {
        return vote_average;
    }

    public float getVote_count() {
        return vote_count;
    }

    public ArrayList<String> getAlso_known_as() {
        return also_known_as;
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

    public String getImdb_id() {
        return imdb_id;
    }

    public String getName() {
        return name;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public String getProfile_path() {
        return BASE_URL_IMG_PROFILE + profile_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public ArrayList<String> getOrigin_country() {
        return origin_country;
    }

    public String getOriginal_name() {
        return original_name;
    }

    @Override
    public String toString() {
        return "Combined{" +
                "adult=" + adult +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", genre_ids=" + genre_ids +
                ", id=" + id +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", poster_path='" + poster_path + '\'' +
                ", release_date='" + release_date + '\'' +
                ", title='" + title + '\'' +
                ", video=" + video +
                ", media_type='" + media_type + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", also_known_as=" + also_known_as +
                ", biography='" + biography + '\'' +
                ", birthday='" + birthday + '\'' +
                ", deathday='" + deathday + '\'' +
                ", gender=" + gender +
                ", homepage='" + homepage + '\'' +
                ", imdb_id='" + imdb_id + '\'' +
                ", name='" + name + '\'' +
                ", place_of_birth='" + place_of_birth + '\'' +
                ", profile_path='" + profile_path + '\'' +
                ", first_air_date='" + first_air_date + '\'' +
                ", origin_country=" + origin_country +
                ", original_name='" + original_name + '\'' +
                '}';
    }
}
