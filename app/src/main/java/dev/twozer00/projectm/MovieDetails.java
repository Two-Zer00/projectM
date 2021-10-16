package dev.twozer00.projectm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import dev.twozer00.projectm.model.Movie;
import dev.twozer00.projectm.model.Person;
import dev.twozer00.projectm.model.Trending;
import dev.twozer00.projectm.model.TvShow;
import dev.twozer00.projectm.ui.main.MovieDetailsPagerAdapter;
import dev.twozer00.projectm.ui.main.SectionsPagerAdapter;

public class MovieDetails extends AppCompatActivity {
    private ImageView backdrop;
    private ImageView poster;
    private TextView overview;
    private Toolbar toolbar;
    private LinearLayout infoContainer;
    private ProgressBar ratingBar;
    private RelativeLayout rateContainer;
    protected CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView rateText;
    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Log.d("ACTIVITY2", "onCreate: ");
        final Movie movie = (Movie) getIntent().getSerializableExtra("media");
        backdrop = findViewById(R.id.backdropImage);
        toolbar = findViewById(R.id.toolbar);
        overview = findViewById(R.id.overview);
        rateContainer = findViewById(R.id.rateContainer);
        poster = findViewById(R.id.poster);
        ratingBar = findViewById(R.id.ratingBar);

        infoContainer = findViewById(R.id.infoContainer);
        rateText = findViewById(R.id.rate);
        collapsingToolbarLayout = findViewById(R.id.barTitle);
        toolbar = findViewById(R.id.toolbar);
        MovieDetailsPagerAdapter sectionsPagerAdapter = new MovieDetailsPagerAdapter(getSupportFragmentManager(), this ,(int)movie.getId());
        ViewPager viewPager = findViewById(R.id.movieDetailsViewPager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
//        ColorStateList colorStateList = tabs.getBackgroundTintList().;
//        colorStateList.getColorForState()
//        tabs.setTabIconTint();
        tabs.setSelectedTabIndicatorColor(getColor(R.color.white));
        tabs.setTabTextColors(getColor(R.color.tabs),getColor(R.color.white));
        tabs.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        loadMovie(movie);
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showImage() {
        Log.d("MODAL IMAGE", "showImage: ");
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(poster.getDrawable());
        AlertDialog.Builder buildera = new AlertDialog.Builder(this);
        buildera.setView(imageView);
        Dialog builder = buildera.create();
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                builder.dismiss();
            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        builder.show();
    }

    private void loadMovie(Movie movie) {
        collapsingToolbarLayout.setTitle(movie.getTitle());
        collapsingToolbarLayout.setExpandedTitleColor(Color.argb(0,1,1,1));
        if(movie.getOriginal_title()!=null){
            toolbar.setSubtitle(movie.getOriginal_title());
        }
        Picasso.get().load(Uri.parse(movie.getBackdrop_path())).into(backdrop);
        Picasso.get().load(Uri.parse(movie.getPoster_path())).error(R.drawable.ic_nocover).into(poster, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable)poster.getDrawable()).getBitmap();
                int colorDominant = Palette.from(bitmap).generate().getDominantColor(getColor(R.color.purple_200));
                int colorDark = Palette.from(bitmap).generate().getDarkVibrantColor(getColor(R.color.purple_700));
                GradientDrawable drawable = new GradientDrawable();
                TabLayout tabs = findViewById(R.id.tabs);
                tabs.setBackgroundColor(colorDark);
                drawable.setColors(new int[]{Color.argb(0,Color.red(colorDark),Color.green(colorDark),Color.blue(colorDark)),colorDark});
                collapsingToolbarLayout.setContentScrim(drawable);
                infoContainer.setBackgroundColor(Color.argb(150,Color.red(colorDominant),Color.green(colorDominant),Color.blue(colorDominant)));
            }

            @Override
            public void onError(Exception e) {

            }
        });
        if(movie.getOverview().length()>250){
            overview.setText(movie.getOverview().substring(0,movie.getOverview().indexOf(". ")+1));
        }
        else{
            overview.setText(movie.getOverview());
        }
        if(movie.getVote_average()>0){
            rateContainer.setVisibility(View.VISIBLE);
            if(movie.getVote_count()>100){
                ratingBar.getProgressDrawable().setColorFilter(getColor(R.color.validRate), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            ratingBar.setProgress((int) (movie.getVote_average()*10),true);
            rateText.setText(String.valueOf(movie.getVote_average()));
        }
        else{
            rateContainer.setTransitionName("");
            rateContainer.setVisibility(View.INVISIBLE);
        }
    }
}