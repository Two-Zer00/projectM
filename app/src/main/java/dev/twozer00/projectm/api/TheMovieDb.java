package dev.twozer00.projectm.api;

import dev.twozer00.projectm.model.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDb {
    @GET("movie/popular")
    Call<MovieResponse> getPopular(@Query("api_key") String api_key, @Query("page") Integer page);

    @GET("person/{person_id}/movie_credits")
    Call<MovieCredits> getMovieCreditsPerson(@Path("person_id") Integer person_id,@Query("api_key") String api_key);

    @GET("person/{person_id}/tv_credits")
    Call<TvShowCredits> getTvCreditsPerson(@Path("person_id") Integer person_id,@Query("api_key") String api_key);

    @GET("movie/upcoming")
    Call<ResponseMovie> getUpcoming(@Query("api_key") String api_key, @Query("page") Integer page);

    @GET("trending/all/week")
    Call<MovieResponse> getTrending(@Query("api_key") String api_key, @Query("page") Integer page);

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") String movie_id,@Query("api_key") String api_key);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewsResponse> getMovieReviews(@Path("movie_id") Integer movie_id, @Query("api_key") String api_key, @Query("page") Integer page);

    @GET("tv/{tv_id}/reviews")
    Call<ReviewsResponse> getTvShowReviews(@Path("tv_id") Integer tv_id, @Query("api_key") String api_key, @Query("page") Integer page);

    @GET("tv/{tv_id}")
    Call<TvShowDetails> getTvShowDetails(@Path("tv_id") Integer tv_id, @Query("api_key") String api_key);

    @GET("movie/{movie_id}/credits")
    Call<Credits> getMovieCredits(@Path("movie_id") Integer movie_id, @Query("api_key") String api_key);

    @GET("tv/{tv_id}/credits")
    Call<Credits> getTvShowCredis(@Path("tv_id") Integer tv_id, @Query("api_key") String api_key);

    @GET("person/{person_id}")
    Call<Person> getPersonDetails(@Path("person_id") Integer person_id, @Query("api_key") String api_key);

    @GET("trending/all/week")
    Call<Response> getTrendingWeek(@Query("api_key") String api_key, @Query("page") Integer page);
}
