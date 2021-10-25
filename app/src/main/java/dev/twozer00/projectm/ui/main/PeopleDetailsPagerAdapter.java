package dev.twozer00.projectm.ui.main;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import dev.twozer00.projectm.MovieFragment;
import dev.twozer00.projectm.PeopleDetailsFragment;
import dev.twozer00.projectm.R;
import dev.twozer00.projectm.TvShowFragment;
import dev.twozer00.projectm.model.Combined;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PeopleDetailsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private final int[] TAB_TITLES = new int[]{R.string.people_details,R.string.details_movies,R.string.details_tv_show};
    private final Context context;
    private final int personId;

    public PeopleDetailsPagerAdapter(@NonNull @NotNull FragmentManager fm, Context context, int personId) {
        super(fm);
        this.context = context;
        this.personId = personId;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new PeopleDetailsFragment(personId);
            break;
            case 1:
                fragment = new MovieFragment(personId);
            break;
            case 2:
                fragment = new TvShowFragment(personId);
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
