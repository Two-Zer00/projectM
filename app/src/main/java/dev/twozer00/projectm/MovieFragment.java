package dev.twozer00.projectm;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.ScrollingView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.twozer00.projectm.adapter.MovieAdapter;
import dev.twozer00.projectm.api.TheMovieDb;
import dev.twozer00.projectm.model.Movie;
import dev.twozer00.projectm.model.MovieResponse;
import dev.twozer00.projectm.model.Trending;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private String query;
    private RecyclerView recyclerView;
    private MovieResponse movieResponse;
    private MovieAdapter movieAdapter;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;
    private boolean isLoading= false;
    View view;

    private static final String API_BASE_URL = "https://api.themoviedb.org/3/" ;
    private static final String API_KEY = "a36aa66b935c743a91a78e97f0e4bc9c";
    private GridLayoutManager layoutManager;

    public MovieFragment(String query) {
        this.query = query;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie,container,false);
        recyclerView = view.findViewById(R.id.recycler);
        nestedScrollView = view.findViewById(R.id.nestedSv);
        progressBar = view.findViewById(R.id.progressBar);
        movieAdapter = new MovieAdapter(view.getContext());
        movieAdapter.setQuery(query);
        layoutManager = new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(movieAdapter);
        getData(1,query);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int last = (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight());
                int recyclerHeight = recyclerView.getMeasuredHeight();
                int lastRow = recyclerView.getChildAt(movieResponse.getResults().size()-1).getHeight();
                Log.d("rows", "onScrollChange: " + scrollY + " " + v.getChildAt(0).getMeasuredHeight() + " " + recyclerView.getMeasuredHeight());
                if(!isLoading){
                    if (scrollY >= ((last)/*-(lastRow*2) performace to be fixed, meanwhile, load all the elements at the bottom*/)) {
                        isLoading = true;
                        Log.d("BB", "gettin data: ");
                        getData(movieResponse.getPage()+1,query);
                        progressBar.setVisibility(View.VISIBLE);
                        ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "translationY", convertDpToPixel(-10,view.getContext()));
                        animation.setDuration(500);
                        animation.start();
                    }
                }
            }
        });
        return view;
    }
    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
    private void getData(int page,String query){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDb movieApi = retrofit.create(TheMovieDb.class);
        Call<MovieResponse> call = call = movieApi.getPopular(API_KEY,page);
        if(query.equals("Popular")){
            call = movieApi.getTrending(API_KEY,page);
        }
        else if(query.equals("Upcoming")){
            call = movieApi.getUpcoming(API_KEY,page);
        }
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.d("RETROFIT init", "Getting response");
                if(response.isSuccessful()){
                    Log.d("Retrofit valid response",response.body().toString());
                    movieResponse = response.body();
                    movieAdapter.addElemList((ArrayList<Trending>) movieResponse.getResults());
                    isLoading = false;
                    ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "translationY", convertDpToPixel(10,view.getContext()));
                    animation.setDuration(500);
                    animation.start();
                    animation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            //progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("Retrofit failure",t.getMessage());
            }

        });
    }


    private ArrayList<Movie> getResults(int page, String query){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDb movieApi = retrofit.create(TheMovieDb.class);
        Call<MovieResponse> call = movieApi.getPopular(API_KEY,page);
        switch (query){
            case "Popular":
                call = movieApi.getTrending(API_KEY,page);
            break;
            case "Upcoming":
                call = movieApi.getUpcoming(API_KEY,page);
            break;
        }
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
        return new ArrayList<Movie>();
    }
}