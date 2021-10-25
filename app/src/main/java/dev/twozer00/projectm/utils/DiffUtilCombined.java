package dev.twozer00.projectm.utils;

import androidx.recyclerview.widget.DiffUtil;
import dev.twozer00.projectm.model.Combined;

import java.util.ArrayList;

public class DiffUtilCombined extends DiffUtil.Callback {

    private ArrayList<Combined> oldList;
    private ArrayList<Combined> newList;

    public DiffUtilCombined(ArrayList<Combined> oldList, ArrayList<Combined> newList) {
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
