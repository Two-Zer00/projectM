package dev.twozer00.projectm;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.twozer00.projectm.api.TheMovieDb;
import dev.twozer00.projectm.model.Genre;
import dev.twozer00.projectm.model.MovieDetails;
import dev.twozer00.projectm.model.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class MovieDetailsFragment extends Fragment {
    private static final String TAG = "MovieDetailsFragment";
    private static final String API_BASE_URL = "https://api.themoviedb.org/3/" ;
    private static final String API_KEY = "a36aa66b935c743a91a78e97f0e4bc9c";
    private View view;
    private int movieId;
    private MovieDetails movieDetails;
    private TextView releaseDate;
    private TextView status;
    private TextView budget;
    private TextView revenue;
    private TextView genres;
    private TextView detailed_overview;
    private TextView language;
    private TextView home_page;
    private TextView duration;


    public MovieDetailsFragment(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDetails(movieId);
    }

    private void loadDetails(int movieId) {
        Log.d(TAG, "loadDetails: " + movieId);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDb movieApi = retrofit.create(TheMovieDb.class);
        Call<MovieDetails> call = movieApi.getMovieDetails(String.valueOf(movieId),API_KEY);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                Log.d("RETROFIT", "onResponse: movieDetails" + response.body().toString() );
                movieDetails = response.body();
                bindData(movieDetails);
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Log.d("RETROFIT", "onFailure: movieDetails" + t.getMessage());
            }
        });
    }

    private void bindData(MovieDetails movieDetails) {
        Log.d("TAG", "bindData: " + movieDetails.toString());
        releaseDate.setText(movieDetails.getRelease_date());
        status.setText(movieDetails.getStatus());
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        budget.setText(movieDetails.getBudget()>0?formatter.format(movieDetails.getBudget()):getString(R.string.movie_details_empty_fields)); // check if value is > 0, if not, shows "Not provided" avoiding shows 0 as revenue/budget
        revenue.setText(movieDetails.getRevenue()>0?formatter.format(movieDetails.getRevenue()):getString(R.string.movie_details_empty_fields)); // check if value is > 0, if not, shows "Not provided" avoiding shows 0 as revenue/budget
        Iterator<Genre> genresObject = movieDetails.getGenres().iterator();
        List<String> genresN = new ArrayList<>();
        while(genresObject.hasNext()){
            genresN.add(genresObject.next().getName());
        }

        long timeInMilliSeconds = (long) Math.floor(movieDetails.getRuntime() * 60 * 1000);
        Date date = new Date(timeInMilliSeconds);
        SimpleDateFormat sdf = new SimpleDateFormat("H.mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        duration.setText(movieDetails.getRuntime()!=0?sdf.format(date)+" "+ getString(R.string.movie_details_duration_measure) :getString(R.string.movie_details_empty_fields));
        genres.setText(genresN.toString().replaceAll("[\\[\\]]",""));
        detailed_overview.setText(movieDetails.getOverview()!=null?movieDetails.getOverview():getString(R.string.movie_details_empty_fields));
        language.setText(movieDetails.getOriginal_language());
        home_page.setText(movieDetails.getHomepage()!=null?movieDetails.getHomepage():getString(R.string.movie_details_empty_fields)); // checking if the fields comes empty if so, show "Not provided" string resource
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie_details,container,false);
        releaseDate = view.findViewById(R.id.releaseDate);
        status = view.findViewById(R.id.status);
        budget = view.findViewById(R.id.budget);
        revenue = view.findViewById(R.id.revenue);
        genres = view.findViewById(R.id.genres);
        detailed_overview = view.findViewById(R.id.detailed_overview);
        language = view.findViewById(R.id.language);
        duration = view.findViewById(R.id.duration);
        home_page = view.findViewById(R.id.home_page);
        return view;
    }
}