package dev.twozer00.projectm.utils;

import androidx.recyclerview.widget.DiffUtil;
import dev.twozer00.projectm.model.Review;
import dev.twozer00.projectm.model.Trending;

import java.util.ArrayList;

public class DiffUtilReviews extends DiffUtil.Callback {
    private ArrayList<Review> oldList;
    private ArrayList<Review> newList;

    public DiffUtilReviews(ArrayList<Review> oldList, ArrayList<Review> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }



    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
