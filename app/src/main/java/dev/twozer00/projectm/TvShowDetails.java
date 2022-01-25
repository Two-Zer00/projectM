package dev.twozer00.projectm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import dev.twozer00.projectm.model.Movie;
import dev.twozer00.projectm.model.TvShow;
import dev.twozer00.projectm.ui.main.MovieDetailsPagerAdapter;
import dev.twozer00.projectm.ui.main.TvShowDetailsPagerAdapter;

public class TvShowDetails extends AppCompatActivity {
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
    private final String TAG = "TVSHOWDETAILS";
    private TvShow tvShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_details);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        //WindowCompat.setDecorFitsSystemWindows(this.getWindow(), false);
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        Log.d(TAG, "onCreate: ");
        tvShow= (TvShow) getIntent().getSerializableExtra("media");
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
        //getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));
        TvShowDetailsPagerAdapter sectionsPagerAdapter = new TvShowDetailsPagerAdapter(getSupportFragmentManager(), this ,(int)tvShow.getId());
        ViewPager viewPager = findViewById(R.id.movieDetailsViewPager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setSelectedTabIndicatorColor(getColor(R.color.white));
        tabs.setTabTextColors(getColor(R.color.tabs),getColor(R.color.white));
        tabs.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        loadTvShow(tvShow);
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });

    }
    private void loadTvShow(TvShow tvShow) {
        collapsingToolbarLayout.setTitle(tvShow.getName());
        if(tvShow.getOriginal_name()!=null){
            toolbar.setSubtitle(tvShow.getOriginal_name());
        }
        Picasso.get().load(Uri.parse(tvShow.getBackdrop_path())).into(backdrop, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable)backdrop.getDrawable()).getBitmap();
                int colorDominant = Palette.from(bitmap).generate().getDominantColor(getColor(R.color.purple_200));
                int colorDark = Palette.from(bitmap).generate().getDarkVibrantColor(getColor(R.color.purple_700));
                if(colorDark==getColor(R.color.purple_700)){
                    colorDark =Palette.from(bitmap).generate().getDarkMutedColor(getColor(R.color.purple_700));
                }
                GradientDrawable drawable = new GradientDrawable();
                TabLayout tabs = findViewById(R.id.tabs);
                tabs.setBackgroundColor(colorDark);
                drawable.setColors(new int[]{Color.argb(0,Color.red(colorDark),Color.green(colorDark),Color.blue(colorDark)),colorDark});
                collapsingToolbarLayout.setContentScrim(drawable);
                //collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.transparent));
                //infoContainer.setBackgroundColor(Color.argb(150,Color.red(colorDominant),Color.green(colorDominant),Color.blue(colorDominant)));
            }

            @Override
            public void onError(Exception e) {

            }
        });
        Picasso.get().load(Uri.parse(tvShow.getPoster_path())).error(R.drawable.ic_nocover).into(poster);
        if(tvShow.getOverview().length()>250){
            overview.setText(tvShow.getOverview().substring(0,tvShow.getOverview().indexOf(". ")+1));
        }
        else{
            overview.setText(tvShow.getOverview());
        }
        if(tvShow.getVote_average()>0){
            rateContainer.setVisibility(View.VISIBLE);
            if(tvShow.getVote_count()>100){
                ratingBar.getProgressDrawable().setColorFilter(getColor(R.color.validRate), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            ratingBar.setProgress((int) (tvShow.getVote_average()*10),true);
            rateText.setText(String.valueOf(tvShow.getVote_average()));
        }
        else{
            rateContainer.setTransitionName("");
            rateContainer.setVisibility(View.INVISIBLE);
        }
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public void showImage() {
        Log.d("MODAL IMAGE", "showImage: ");
        ImageView imageView = new ImageView(this);
        Picasso.get().load(Uri.parse((tvShow.getPoster_path()).replace("/w500/","/original/"))).placeholder(R.drawable.ic_placeholder).into(imageView);
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
}