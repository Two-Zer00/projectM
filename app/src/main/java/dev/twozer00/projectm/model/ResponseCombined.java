package dev.twozer00.projectm.model;

import java.util.ArrayList;

public class ResponseCombined extends Response{
    private ArrayList<Combined> results ;

    @Override
    public ArrayList<Combined> getResults() {
        return results;
    }

    @Override
    public void setResults(ArrayList<Combined> results) {
        this.results = results;
    }
}
