package dev.twozer00.projectm;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

import static dev.twozer00.projectm.utils.Constants.API_BASE_URL;
import static dev.twozer00.projectm.utils.Constants.API_KEY;
import static dev.twozer00.projectm.utils.Utils.dpToPx;

public class ReviewFragment extends Fragment {

    private final String TAG = "Review Fragment";

    private int media_id;
    private int mediaType;
    private ArrayList<Review> reviews;
    private RecyclerView recyclerView;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;

    private boolean isLoading= false;

    private TextView empty;
    private ReviewAdapter reviewAdapter;
    private GridLayoutManager layoutManager;
    private int totalResults=0;
    private int page=1;
    ReviewsResponse reviewsResponse;

    private Callback<ReviewsResponse> callback = new Callback<ReviewsResponse>() {
        @Override
        public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
            if (response.isSuccessful()){
                reviewsResponse = response.body();
                totalResults = response.body().getTotal_results();
                reviews = response.body().getResults();
                page = response.body().getPage();
                reviewAdapter.addElemList(reviews);
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onResponse: success " + reviews.toString());
                if(reviews.isEmpty()){
                    empty.setVisibility(View.VISIBLE);
                }
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

    public ReviewFragment(int media_id,int mediaType) {
        this.media_id = media_id;
        this.mediaType = mediaType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void downloadData(int media_id,int page) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDb movieApi = retrofit.create(TheMovieDb.class);
        Log.d(TAG, "downloadData: " + media_id + " " + API_KEY + " " + page);
        Call<ReviewsResponse> call = null;
        switch (mediaType){
            case 0:
                call = movieApi.getMovieReviews(media_id,API_KEY,page);
                call.enqueue(callback);
            break;
            case 1:
                call = movieApi.getTvShowReviews(media_id,API_KEY,page);
                call.enqueue(callback);
        }
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
        empty = view.findViewById(R.id.empty);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int last = (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight());
                int recyclerHeight = recyclerView.getMeasuredHeight();
                int lastRow = recyclerView.getChildAt(reviewsResponse.getResults().size()-1).getHeight();
                Log.d("rows", "onScrollChange: " + scrollY + " " + v.getChildAt(0).getMeasuredHeight() + " " + recyclerView.getMeasuredHeight());
                if(!isLoading){
                    if (scrollY >= ((last)/*-(lastRow*2) performace to be fixed, meanwhile, load all the elements at the bottom*/)) {
                        isLoading = true;
                        Log.d("BB", "gettin data: ");
                        if(reviewsResponse.getPage()+1<reviewsResponse.getTotal_pages()){
                            downloadData(media_id,reviewsResponse.getPage()+1);
                            ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "translationY", dpToPx(-10,view.getContext()));
                            animation.setDuration(500);
                            animation.start();
                        }
                        else{
                            Toast.makeText(view.getContext(), R.string.no_more_results, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        downloadData(media_id,1);
    }
}