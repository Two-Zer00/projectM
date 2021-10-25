package dev.twozer00.projectm;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import dev.twozer00.projectm.adapter.CreditAdapter;
import dev.twozer00.projectm.adapter.ReviewAdapter;
import dev.twozer00.projectm.api.TheMovieDb;
import dev.twozer00.projectm.model.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Locale;

import static dev.twozer00.projectm.utils.Constants.API_BASE_URL;
import static dev.twozer00.projectm.utils.Constants.API_KEY;


public class CreditFragment extends Fragment {

    private static final String TAG = "CREDIT FRAGMENT";
    private int media_id;
    private int mediaType;
    private ArrayList<Cast> cast;
    private ArrayList<Crew> crew;
    private RecyclerView recyclerView;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;
    private CreditAdapter creditAdapter;
    private GridLayoutManager layoutManager;
    private Spinner spinner;
    private Callback<Credits> callback = new Callback<Credits>() {
        @Override
        public void onResponse(Call<Credits> call, Response<Credits> response) {
            if (response.isSuccessful()){
                cast = response.body().getCast();
                crew = response.body().getCrew();
                progressBar.setVisibility(View.GONE);
                creditAdapter.setCreditsType("cast"); // default select item
                creditAdapter.addElemList(cast);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:
                                creditAdapter.setCreditsType("cast");
                                creditAdapter.addElemList(cast);
                                if(cast.isEmpty()){
                                    Toast.makeText(view.getContext(), "No results", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 1:
                                creditAdapter.setCreditsType("crew");
                                creditAdapter.addElemList(crew);
                                if(crew.isEmpty()){
                                    Toast.makeText(view.getContext(), "No results", Toast.LENGTH_SHORT).show();
                                }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //creditAdapter.addElemList(crew);
                //Log.d(TAG, "onResponse: success " + cast.toString());
            }
            else{
                Log.e(TAG, "onResponse: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<Credits> call, Throwable t) {
            Log.e(TAG, "onResponse failure: " + t.getMessage() +" " + call.request().url());
        }
    };


    public CreditFragment(int media_id, int mediaType) {
        this.media_id = media_id;
        this.mediaType = mediaType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void downloadData() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDb movieApi = retrofit.create(TheMovieDb.class);
        Log.d(TAG, "downloadData: " + media_id + " " + API_KEY);
        Call<Credits> call = null;
        switch (mediaType){
            case 0:
                call = movieApi.getMovieCredits(media_id,API_KEY);
                call.enqueue(callback);
                break;
            case 1:
                call = movieApi.getTvShowCredis(media_id,API_KEY);
                call.enqueue(callback);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_credit, container, false);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = view.findViewById(R.id.credits);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        recyclerView = view.findViewById(R.id.recycler);
        nestedScrollView = view.findViewById(R.id.nestedSv);
        recyclerView.setHasFixedSize(false);
        creditAdapter = new CreditAdapter(view.getContext(),"");
        layoutManager = new GridLayoutManager(view.getContext(),3);
        recyclerView.setAdapter(creditAdapter);
        recyclerView.setLayoutManager(layoutManager);
        progressBar = view.findViewById(R.id.progress_circle);
        new Thread(this::downloadData).start();
        return view;
    }
}