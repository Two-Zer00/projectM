package dev.twozer00.projectm;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.twozer00.projectm.api.TheMovieDb;
import dev.twozer00.projectm.model.Genre;
import dev.twozer00.projectm.model.MovieDetails;
import dev.twozer00.projectm.model.TvShowDetails;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.SimpleDateFormat;
import java.util.*;

import static dev.twozer00.projectm.utils.Constants.API_BASE_URL;
import static dev.twozer00.projectm.utils.Constants.API_KEY;


public class TvShowDetailsFragment extends Fragment {

    private final int tvShow_id;
    private final String TAG = "TV SHOW FRAGMENT";
    private TvShowDetails tvShowDetails;
    private TextView releaseDate;
    private TextView lastAirDate;
    private TextView episodesNumber;
    private TextView seasonNumber;
    private TextView status;
    private TextView genres;
    private TextView type;
    private TextView detailed_overview;
    private TextView language;
    private TextView home_page;
    private TextView duration;

    public TvShowDetailsFragment(int tvShow_id) {
        this.tvShow_id = tvShow_id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void loadDetails(int tvShow_id) {
        Log.d(TAG, "loadDetails: " + tvShow_id);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDb movieApi = retrofit.create(TheMovieDb.class);
        Call<TvShowDetails> call = movieApi.getTvShowDetails(tvShow_id,API_KEY);
        call.enqueue(new Callback<TvShowDetails>() {
            @Override
            public void onResponse(Call<TvShowDetails> call, Response<TvShowDetails> response) {
                Log.d("RETROFIT", "onResponse: tvShowDetails" + response.body().toString() );
                tvShowDetails = response.body();
                bindData(tvShowDetails);
            }

            @Override
            public void onFailure(Call<TvShowDetails> call, Throwable t) {
                Log.d("RETROFIT", "onFailure: tvShowDetails" + t.getMessage());
            }
        });
    }

    public void bindData(TvShowDetails tvShowDetails){
        Log.d(TAG, "bindData: " + tvShowDetails.toString() );
        releaseDate.setText(tvShowDetails.getFirst_air_date());
        status.setText(tvShowDetails.getStatus());
        Iterator<Genre> genresObject = tvShowDetails.getGenres().iterator();
        List<String> genresN = new ArrayList<>();
        while(genresObject.hasNext()){
            genresN.add(genresObject.next().getName());
        }
        language.setText(tvShowDetails.getOriginal_language());
        home_page.setText(tvShowDetails.getHomepage());
        lastAirDate.setText(tvShowDetails.getLast_air_date());
        episodesNumber.setText(String.valueOf(tvShowDetails.getNumber_of_episodes()));
        seasonNumber.setText(String.valueOf(tvShowDetails.getNumber_of_seasons()));
        detailed_overview.setText(tvShowDetails.getOverview());
        type.setText(tvShowDetails.getType());
        duration.setText(tvShowDetails.getEpisode_run_time().get(tvShowDetails.getEpisode_run_time().size()-1)!=0?tvShowDetails.getEpisode_run_time().get(tvShowDetails.getEpisode_run_time().size()-1)+" "+ getString(R.string.details_duration_measure_min) :getString(R.string.empty_fields));
        genres.setText(genresN.toString().replaceAll("[\\[\\]]",""));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show_details, container, false);
        releaseDate = view.findViewById(R.id.releaseDate);
        status = view.findViewById(R.id.status);
        genres = view.findViewById(R.id.genres);
        detailed_overview = view.findViewById(R.id.detailed_overview);
        language = view.findViewById(R.id.language);
        duration = view.findViewById(R.id.duration);
        home_page = view.findViewById(R.id.home_page);
        lastAirDate = view.findViewById(R.id.last_air_date);
        episodesNumber= view.findViewById(R.id.episodes_number);
        seasonNumber = view.findViewById(R.id.seasons_number);
        type = view.findViewById(R.id.type);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(tvShowDetails==null){
            loadDetails(tvShow_id);
        }
        else{
            bindData(tvShowDetails);
        }
    }
}