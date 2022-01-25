package dev.twozer00.projectm.model;

import java.util.ArrayList;

public class ResponseSearch {
    private float page;
    ArrayList < Combined > results = new ArrayList<>();
    private float total_results;
    private float total_pages;

    public ResponseSearch(float page, ArrayList<Combined> results, float total_results, float total_pages) {
        this.page = page;
        this.results = results;
        this.total_results = total_results;
        this.total_pages = total_pages;
    }

    public ArrayList<Combined> getResults() {
        return results;
    }

    public void setResults(ArrayList<Combined> results) {
        this.results = results;
    }

    // Getter Methods

    public float getPage() {
        return page;
    }

    public float getTotal_results() {
        return total_results;
    }

    public float getTotal_pages() {
        return total_pages;
    }

    // Setter Methods

    public void setPage(float page) {
        this.page = page;
    }

    public void setTotal_results(float total_results) {
        this.total_results = total_results;
    }

    public void setTotal_pages(float total_pages) {
        this.total_pages = total_pages;
    }
}
