package dev.twozer00.projectm.utils;

import androidx.recyclerview.widget.DiffUtil;
import dev.twozer00.projectm.model.Movie;

import java.util.ArrayList;

public class DiffUtilMovie extends DiffUtil.Callback {
    private ArrayList<Movie> oldList;
    private ArrayList<Movie> newList;

    public DiffUtilMovie(ArrayList<Movie> oldList, ArrayList<Movie> newList) {
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
