package dev.twozer00.projectm;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.twozer00.projectm.adapter.CombinedAdapter;
import dev.twozer00.projectm.api.TheMovieDb;
import dev.twozer00.projectm.model.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static dev.twozer00.projectm.utils.Constants.API_BASE_URL;
import static dev.twozer00.projectm.utils.Constants.API_KEY;
import static dev.twozer00.projectm.utils.Utils.dpToPx;

public class CombinedFragment extends Fragment {
    private View view;
    private String media_type;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;
    private CombinedAdapter combinedAdapter;
    private Integer page = 1;
    private Response responseApi;
    private Boolean isLoading = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_combined, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        nestedScrollView = view.findViewById(R.id.nestedSv);
        progressBar = view.findViewById(R.id.progressBar);
        combinedAdapter = new CombinedAdapter(getContext(),getActivity());
        gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setAdapter(combinedAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        getData(page);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int last = (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight());
                int recyclerHeight = recyclerView.getMeasuredHeight();
                int lastRow = recyclerView.getChildAt(responseApi.getResults().size()-1).getHeight();
                Log.d("rows", "onScrollChange: " + scrollY + " " + v.getChildAt(0).getMeasuredHeight() + " " + recyclerView.getMeasuredHeight());
                if(!isLoading){
                    if (scrollY >= ((last)/*-(lastRow*2) performace to be fixed, meanwhile, load all the elements at the bottom*/)) {
                        isLoading = true;
                        Log.d("BB", "gettin data: ");
                        if(responseApi.getPage()+1<responseApi.getTotal_pages()){
                            page = responseApi.getPage() + 1;
                            getData(page);
                            ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "translationY", dpToPx(-10,view.getContext()));
                            animation.setDuration(500);
                            animation.start();
                        }
                        else{
                            Toast.makeText(getContext(), R.string.no_more_results , Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        return view;
    }

    private void getData(Integer page) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDb movieApi = retrofit.create(TheMovieDb.class);
        Call<Response> call = movieApi.getTrendingWeek(API_KEY,page);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                responseApi = response.body();
                combinedAdapter.addList(response.body().getResults());
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
            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }
}