package dev.twozer00.projectm.ui.main;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import dev.twozer00.projectm.MovieDetailsFragment;
import dev.twozer00.projectm.R;
import dev.twozer00.projectm.ReviewFragment;
import org.jetbrains.annotations.NotNull;

public class MovieDetailsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private final int[] TAB_TITLES = new int[]{R.string.movie_details,R.string.movie_details_review};

    private final Context context;
    private final int movieId;

    public MovieDetailsPagerAdapter(@NonNull @NotNull FragmentManager fm, Context context, int movieId) {
        super(fm);
        this.context = context;
        this.movieId = movieId;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new MovieDetailsFragment(movieId);
                break;
            case 1:
                fragment = new ReviewFragment(movieId);
                break;
        }
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return fragment;
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}
