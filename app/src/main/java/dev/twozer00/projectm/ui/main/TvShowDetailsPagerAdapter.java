package dev.twozer00.projectm.ui.main;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import dev.twozer00.projectm.*;
import org.jetbrains.annotations.NotNull;

public class TvShowDetailsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private final int[] TAB_TITLES = new int[]{R.string.tv_show_details,R.string.details_review,R.string.details_credits};

    private final Context context;
    private final int tvShowId;

    public TvShowDetailsPagerAdapter(@NonNull @NotNull FragmentManager fm, Context context, int tvShowId) {
        super(fm);
        this.context = context;
        this.tvShowId = tvShowId;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new TvShowDetailsFragment(tvShowId);
                break;
            case 1:
                fragment = new ReviewFragment(tvShowId,1);
                break;
            case 2:
                fragment = new CreditFragment(tvShowId,1);
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
