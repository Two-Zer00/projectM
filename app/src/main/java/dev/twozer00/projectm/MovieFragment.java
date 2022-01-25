package dev.twozer00.projectm;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.twozer00.projectm.adapter.MovieAdapter;
import dev.twozer00.projectm.api.TheMovieDb;
import dev.twozer00.projectm.model.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static dev.twozer00.projectm.utils.Utils.dpToPx;

public class MovieFragment extends Fragment {

    private String query;
    private RecyclerView recyclerView;
    private ResponseMovie responseMovie;
    private MovieAdapter movieAdapter;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;
    private boolean isLoading= false;
    private Integer person_id;
    private Integer page=1;
    private ArrayList<Movie> movies;
    private Spinner spinner;
    private ArrayList<Movie> cast;
    private ArrayList<Movie> crew;
    View view;

    private static final String API_BASE_URL = "https://api.themoviedb.org/3/" ;
    private static final String API_KEY = "a36aa66b935c743a91a78e97f0e4bc9c";
    private GridLayoutManager layoutManager;

    public MovieFragment(String query) {
        this.query = query;
    }

    public MovieFragment(Integer person_id) {
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
        view = inflater.inflate(R.layout.fragment_movie,container,false);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = view.findViewById(R.id.spinner);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        recyclerView = view.findViewById(R.id.recycler);
        nestedScrollView = view.findViewById(R.id.nestedSv);
        progressBar = view.findViewById(R.id.progressBar);
        movieAdapter = new MovieAdapter(view.getContext());
        layoutManager = new GridLayoutManager(view.getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(movieAdapter);
        getData();
        if (query!=null){ // fetch data on finish scroll
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    int last = (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight());
                    Log.d("rows", "onScrollChange: " + scrollY + " " + v.getChildAt(0).getMeasuredHeight() + " " + recyclerView.getMeasuredHeight());
                    if(!isLoading){
                        if (scrollY >= ((last)/*-(lastRow*2) performace to be fixed, meanwhile, load all the elements at the bottom*/)) {
                            isLoading = true;
                            Log.d("BB", "gettin data: ");
                            if(responseMovie.getPage()+1<responseMovie.getTotal_pages()){
                                getData();
                                ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "translationY", dpToPx(-10,view.getContext()));
                                animation.setDuration(500);
                                animation.start();
                            }
                        }
                    }
                }
            });
        }
        return view;
    }

    private void getData(){
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDb movieApi = retrofit.create(TheMovieDb.class);
        if(query!=null){
            Call<ResponseMovie> call = movieApi.getUpcoming(API_KEY,page);
            call.enqueue(new Callback<ResponseMovie>() {
                @Override
                public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                    Log.d("RETROFIT init", "Getting response");
                    if(response.isSuccessful()){
                        Log.d("Retrofit valid response",response.body().toString());
                        responseMovie = response.body();
                        page = responseMovie.getPage() + 1;
//                        responseMovie.getResults().removeIf(movie -> {
//                            //String releaseDate = movie.getRelease_date();
//                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//                            Date convertedDate = new Date();
//                            try {
//                                if (movie.getRelease_date() != null) {
//                                    Objects.requireNonNull(movie.getRelease_date());
//                                    convertedDate = dateFormat.parse(movie.getRelease_date());
//                                }
//                            } catch (ParseException e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//                            DateFormat Date = DateFormat.getDateInstance();
//                            Calendar cal = Calendar.getInstance();
//                            cal.setTime(convertedDate);
//                            //Log.d("TIME", "onBindViewHolder: " + System.currentTimeMillis() + " " + convertedDate.getTime() );
//                            if(convertedDate.getTime()>System.currentTimeMillis()){
//                                return false;
//                            }
//                            else{
//                                return true;
//                            }
//                        });
                        movieAdapter.addElemList((ArrayList<Movie>) responseMovie.getResults());
                        isLoading = false;
                        ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "translationY", dpToPx(10,view.getContext()));
                        animation.setDuration(500);
                        animation.start();
                        animation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                progressBar.setVisibility(View.INVISIBLE);
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
                public void onFailure(Call<ResponseMovie> call, Throwable t) {
                    Log.e("Retrofit failure",t.getMessage());
                }

            });
        }
        if(person_id!=null){
            spinner.setVisibility(View.VISIBLE);
            Call<MovieCredits> call = movieApi.getMovieCreditsPerson(person_id,API_KEY);
            call.enqueue(new Callback<MovieCredits>() {
                @Override
                public void onResponse(Call<MovieCredits> call, Response<MovieCredits> response) {
                    Log.d("RETROFIT init", "Getting response");
                    if(response.isSuccessful()){
                        Log.d("Retrofit valid response",response.body().toString());
                        cast = response.body().getCast();
                        crew = response.body().getCrew();
                        movieAdapter.replaceListElements(cast);
                        progressBar.setVisibility(View.GONE);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                switch (position){
                                    case 0:
                                        movieAdapter.replaceListElements(cast);
                                        break;
                                    case 1:
                                        movieAdapter.replaceListElements(crew);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
                @Override
                public void onFailure(Call<MovieCredits> call, Throwable t) {
                    Log.e("Retrofit failure",t.getMessage());
                }

            });
        }
    }
}