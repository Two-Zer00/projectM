package dev.twozer00.projectm.api;

import dev.twozer00.projectm.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDb {
    @GET("movie/popular")
    Call<MovieResponse> getPopular(@Query("api_key") String api_key, @Query("page") Integer page);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcoming(@Query("api_key") String api_key, @Query("page") Integer page);

    @GET("trending/all/week")
    Call<MovieResponse> getTrending(@Query("api_key") String api_key, @Query("page") Integer page);

}
