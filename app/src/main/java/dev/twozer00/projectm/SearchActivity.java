package dev.twozer00.projectm;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.twozer00.projectm.adapter.SearchAdapter;
import dev.twozer00.projectm.api.TheMovieDb;
import dev.twozer00.projectm.model.Combined;
import dev.twozer00.projectm.model.ResponseSearch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

import static dev.twozer00.projectm.utils.Constants.API_BASE_URL;
import static dev.twozer00.projectm.utils.Constants.API_KEY;

public class SearchActivity extends AppCompatActivity {
    private ResponseSearch responseMovie;
    private SearchAdapter searchAdapter;
    private RecyclerView recyclerView;
    Handler mHandler = new Handler();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        searchAdapter = new SearchAdapter(getApplicationContext());
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.loader);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchAdapter);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        // Define the listener
        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item)
            {
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                // Do something when action item collapses
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item)
            {

                // Do something when expanded
                return true;  // Return true to expand action view
            }
        };

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        // Assign the listener to that action item
        MenuItemCompat.setOnActionExpandListener(menuItem, expandListener);

        // Any other things you have to do when creating the options menu…
        menuItem.expandActionView();

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        // Get the search close button image view
        ImageView closeButton = (ImageView)searchView.findViewById(R.id.search_close_btn);
        // Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("CLOSE BUTTON", "Search close button clicked");
                //Find EditText view
                EditText et = (EditText) findViewById(R.id.search_src_text);

                //Clear the text from EditText view
                et.setText("");

                //Clear query
                searchView.setQuery("", false);
                searchAdapter.cleanList();

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                if(!newText.isEmpty()){
                    String mQueryString = newText;

                    mHandler.removeCallbacksAndMessages(null);

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                            Log.d("TAG", "run: "+mQueryString);
                            doMySearch(mQueryString);
                        }
                    }, 500);
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void doMySearch(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDb movieApi = retrofit.create(TheMovieDb.class);
        Call<ResponseSearch> call = movieApi.getSearch(API_KEY,query,1);
        call.enqueue(new Callback<ResponseSearch>() {
            @Override
            public void onResponse(Call<ResponseSearch> call, Response<ResponseSearch> response) {
                if(response.isSuccessful()){
                    responseMovie = response.body();
                    Log.d("TAG", "onResponse:"+responseMovie.getResults());
                    searchAdapter.addList((ArrayList<Combined>) responseMovie.getResults());
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else{
                    Log.e("TAG", "onResponse: "+response.toString());
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseSearch> call, Throwable t) {

            }
        });
    }

}