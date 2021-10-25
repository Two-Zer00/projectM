package dev.twozer00.projectm;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.twozer00.projectm.adapter.CombinedAdapter;
import dev.twozer00.projectm.adapter.MovieAdapter;
import dev.twozer00.projectm.adapter.TvShowAdapter;
import dev.twozer00.projectm.api.TheMovieDb;
import dev.twozer00.projectm.model.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

import static dev.twozer00.projectm.utils.Constants.API_BASE_URL;
import static dev.twozer00.projectm.utils.Constants.API_KEY;


public class TvShowFragment extends Fragment {
    private RecyclerView recyclerView;
    private ResponseMovie responseMovie;
    private TvShowAdapter tvShowAdapter;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;
    private boolean isLoading= false;
    private Integer person_id;
    private Integer page=1;
    private ArrayList<TvShow> tvShows;
    private GridLayoutManager layoutManager;
    private Spinner spinner;
    private ArrayList<TvShow> cast;
    private ArrayList<TvShow> crew;
    View view;

    public TvShowFragment(Integer person_id) {
        this.person_id = person_id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = view.findViewById(R.id.spinner);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        recyclerView = view.findViewById(R.id.recycler);
        nestedScrollView = view.findViewById(R.id.nestedSv);
        progressBar = view.findViewById(R.id.progressBar);
        tvShowAdapter = new TvShowAdapter(view.getContext());
        layoutManager = new GridLayoutManager(view.getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(tvShowAdapter);
        getData();
        return view;
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDb movieApi = retrofit.create(TheMovieDb.class);
        Call<TvShowCredits> call = movieApi.getTvCreditsPerson(person_id,API_KEY);
        call.enqueue(new Callback<TvShowCredits>() {
            @Override
            public void onResponse(Call<TvShowCredits> call, Response<TvShowCredits> response) {
                cast = response.body().getCast();
                crew = response.body().getCrew();
                tvShowAdapter.replaceListElements(cast);
                progressBar.setVisibility(View.GONE);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:
                                tvShowAdapter.replaceListElements(cast);
                                break;
                            case 1:
                                tvShowAdapter.replaceListElements(crew);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<TvShowCredits> call, Throwable t) {

            }
        });
    }
}