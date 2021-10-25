package dev.twozer00.projectm.utils;

import androidx.recyclerview.widget.DiffUtil;
import dev.twozer00.projectm.model.Cast;
import dev.twozer00.projectm.model.Crew;
import dev.twozer00.projectm.model.Review;

import java.util.ArrayList;

public class DiffUtilCredits extends DiffUtil.Callback {
    private ArrayList oldList;
    private ArrayList newList;
    private String creditType;

    public DiffUtilCredits(ArrayList oldList, ArrayList newList,String creditType) {
        this.oldList = oldList;
        this.newList = newList;
        this.creditType = creditType;
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
        if(creditType.equals("cast")){
            ArrayList <Cast> castOld = (ArrayList<Cast>) oldList;
            ArrayList <Cast> castsNew = (ArrayList<Cast>) newList;
            return castOld.get(oldItemPosition).getId() == castsNew.get(newItemPosition).getId();
        }
        else {
            ArrayList <Crew> castOld = (ArrayList<Crew>) oldList;
            ArrayList <Crew> castsNew = (ArrayList<Crew>) newList;
            return castOld.get(oldItemPosition).getId() == castsNew.get(newItemPosition).getId();
        }
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
