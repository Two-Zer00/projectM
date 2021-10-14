package dev.twozer00.projectm.utils;

import androidx.recyclerview.widget.DiffUtil;
import dev.twozer00.projectm.model.Movie;
import dev.twozer00.projectm.model.Trending;

import java.util.ArrayList;

public class MyDiffUtil extends DiffUtil.Callback{
    private ArrayList<Trending> oldList;
    private ArrayList<Trending> newList;

    public MyDiffUtil(ArrayList<Trending> oldList, ArrayList<Trending> newList) {
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
