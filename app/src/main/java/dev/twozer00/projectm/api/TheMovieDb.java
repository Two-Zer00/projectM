package dev.twozer00.projectm.api;

import dev.twozer00.projectm.model.ReviewsResponse;
import dev.twozer00.projectm.model.MovieDetails;
import dev.twozer00.projectm.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDb {
    @GET("movie/popular")
    Call<MovieResponse> getPopular(@Query("api_key") String api_key, @Query("page") Integer page);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcoming(@Query("api_key") String api_key, @Query("page") Integer page);

    @GET("trending/all/week")
    Call<MovieResponse> getTrending(@Query("api_key") String api_key, @Query("page") Integer page);

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") String movie_id,@Query("api_key") String api_key);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewsResponse> getReviews(@Path("movie_id") Integer movie_id, @Query("api_key") String api_key, @Query("page") Integer page);
}
