package dev.twozer00.projectm;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.twozer00.projectm.adapter.ReviewAdapter;
import dev.twozer00.projectm.api.TheMovieDb;
import dev.twozer00.projectm.model.ReviewsResponse;
import dev.twozer00.projectm.model.Review;
import dev.twozer00.projectm.utils.GridAutofitLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

import static dev.twozer00.projectm.utils.Constants.API_BASE_URL;
import static dev.twozer00.projectm.utils.Constants.API_KEY;

public class ReviewFragment extends Fragment {

    private final String TAG = "Review Fragment";

    private int movie_id;
    private ArrayList<Review> reviews;
    private RecyclerView recyclerView;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;
    private ReviewAdapter reviewAdapter;
    private GridLayoutManager layoutManager;
    private int totalResults=0;
    private int page=1;

    private Callback<ReviewsResponse> callback = new Callback<ReviewsResponse>() {
        @Override
        public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
            if (response.isSuccessful()){
                totalResults = response.body().getTotal_results();
                reviews = response.body().getResults();
                page = response.body().getPage();
                reviewAdapter.addElemList(reviews);
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onResponse: success " + reviews.toString());
            }
            else{
                Log.e(TAG, "onResponse: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<ReviewsResponse> call, Throwable t) {
            Log.e(TAG, "onResponse failure: " + t.getMessage() +" " + call.request().url());
        }
    };

    public ReviewFragment(int movie_id) {
        this.movie_id = movie_id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void downloadData(int movie_id,int page) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDb movieApi = retrofit.create(TheMovieDb.class);
        Log.d(TAG, "downloadData: " + movie_id + " " + API_KEY + " " + page);
        Call<ReviewsResponse> call = movieApi.getReviews(movie_id,API_KEY,page);
        call.enqueue(callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        nestedScrollView = view.findViewById(R.id.nestedSv);
        reviewAdapter = new ReviewAdapter(view.getContext());
        layoutManager = new GridLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(reviewAdapter);
        recyclerView.setLayoutManager(layoutManager);
        progressBar = view.findViewById(R.id.progress_circle);
        downloadData(movie_id,1);
        return view;
    }
}