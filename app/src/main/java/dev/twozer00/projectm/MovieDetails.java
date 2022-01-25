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
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import dev.twozer00.projectm.model.Movie;
import dev.twozer00.projectm.model.Person;
import dev.twozer00.projectm.model.Trending;
import dev.twozer00.projectm.model.TvShow;
import dev.twozer00.projectm.ui.main.MovieDetailsPagerAdapter;
import dev.twozer00.projectm.ui.main.SectionsPagerAdapter;
import android.view.WindowManager.LayoutParams;
import android.view.Window;

import static dev.twozer00.projectm.utils.Constants.BASE_URL_IMG;

public class MovieDetails extends AppCompatActivity {
    private ImageView backdrop;
    private ImageView poster;
    private TextView overview;
    private Toolbar toolbar;
    private LinearLayout infoContainer;
    private LinearLayout bottom;
    private ProgressBar ratingBar;
    private RelativeLayout rateContainer;
    protected CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView rateText;
    private TabLayout tabs;
    private Movie movie = new Movie();
    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            //poster.setImageBitmap(bitmap);
            backdrop.setImageBitmap(bitmap);
            int colorDominant = Palette.from(bitmap).generate().getDominantColor(getColor(R.color.purple_200));
            int colorDark = Palette.from(bitmap).generate().getDarkVibrantColor(getColor(R.color.purple_700));
            if(colorDark==getColor(R.color.purple_700)){
                colorDark =Palette.from(bitmap).generate().getDarkMutedColor(getColor(R.color.purple_700));
            }
            GradientDrawable drawable = new GradientDrawable();
            TabLayout tabs = findViewById(R.id.tabs);
            tabs.setBackgroundColor(colorDark);
            tabs.setTabRippleColor(ColorStateList.valueOf(colorDominant));
            drawable.setColors(new int[]{Color.argb(0,Color.red(colorDark),Color.green(colorDark),Color.blue(colorDark)),colorDark});
            collapsingToolbarLayout.setContentScrim(drawable);
            //bottom.back(new int[]{Color.argb(0,Color.red(colorDark),Color.green(colorDark),Color.blue(colorDark)),colorDark});
            //bottom.setBackground(drawable);
            //infoContainer.setBackgroundColor(Color.argb(150,Color.red(colorDominant),Color.green(colorDominant),Color.blue(colorDominant)));
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            poster.setImageResource(R.drawable.ic_nocover);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            poster.setImageResource(R.drawable.background);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Log.d("ACTIVITY2", "onCreate: ");
        movie = (Movie) getIntent().getSerializableExtra("media");
        backdrop = findViewById(R.id.backdropImage);
        toolbar = findViewById(R.id.toolbar);
        overview = findViewById(R.id.overview);
        rateContainer = findViewById(R.id.rateContainer);
        poster = findViewById(R.id.poster);
        ratingBar = findViewById(R.id.ratingBar);
        bottom = findViewById(R.id.bottomColor);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);


        /*
        To enable window content transitions in your code instead, call the Window.requestFeature() method:
         */

        /*
        If you have set an enter transition for the second activity,
        the transition is also activated when the activity starts.
         */
        //WindowCompat.setDecorFitsSystemWindows(this.getWindow(), false);
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        //window.setStatusBarContrastEnforced(true);
        //window.setNavigationBarColor(this.getResources().getColor(android.R.color.transparent));


        infoContainer = findViewById(R.id.infoContainer);
        rateText = findViewById(R.id.rate);
        collapsingToolbarLayout = findViewById(R.id.barTitle);
        toolbar = findViewById(R.id.toolbar);
        MovieDetailsPagerAdapter sectionsPagerAdapter = new MovieDetailsPagerAdapter(getSupportFragmentManager(), this ,(int)movie.getId());
        ViewPager viewPager = findViewById(R.id.movieDetailsViewPager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishAfterTransition();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showImage() {
        Log.d("MODAL IMAGE", "showImage: ");
        ImageView imageView = new ImageView(this);
        Picasso.get().load(Uri.parse((movie.getPoster_path()).replace("/w500/","/original/"))).placeholder(R.drawable.ic_placeholder).into(imageView);
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
        if(movie.getOriginal_title()!=null){
            toolbar.setSubtitle(movie.getOriginal_title());
        }
        if(movie.getBackdrop_path().contains("null")){
            Picasso.get().load(Uri.parse(movie.getPoster_path())).into(target);
        }
        else{
            Picasso.get().load(Uri.parse(movie.getBackdrop_path())).into(target);
        }
        //Log.d("IMAGE POSTER", "loadMovie: "+Uri.parse(movie.getPoster_path()));
        //Picasso.get().load(Uri.parse(movie.getBackdrop_path())).into(target);
        Picasso.get().load(Uri.parse(movie.getPoster_path())).placeholder(R.drawable.background).into(poster);
        //WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content).getRootView(), (v, windowInsets) -> {
//            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
//            // Apply the insets as a margin to the view. Here the system is setting
//            // only the bottom, left, and right dimensions, but apply whichever insets are
//            // appropriate to your layout. You can also update the view padding
//            // if that's more appropriate.
//            android.view.WindowManager.LayoutParams.MarginLayoutParams mlp = (MarginLayoutParams) v.getLayoutParams();
//            mlp.leftMargin = insets.left;
//            mlp.bottomMargin = insets.bottom;
//            mlp.rightMargin = insets.right;
//            v.setLayoutParams(mlp);
//
//            // Return CONSUMED if you don't want want the window insets to keep being
//            // passed down to descendant views.
//            return WindowInsetsCompat.CONSUMED;
//        });

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
            rateText.setText(String.valueOf(movie.getVote_average()).replace(".0",""));
        }
        else{
            rateContainer.setTransitionName("");
            rateContainer.setVisibility(View.INVISIBLE);
        }
    }
}