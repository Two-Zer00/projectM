package dev.twozer00.projectm.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewsResponse implements Serializable {
        private int id;
        private int page;
        private ArrayList<Review> results ;
        private int total_pages;
        private int total_results;

        public ReviewsResponse() {
        }
        public ReviewsResponse(int page, ArrayList<Review> results, int total_pages, int total_results, int id) {
            this.page = page;
            this.id = id;
            this.results = results;
            this.total_pages = total_pages;
            this.total_results = total_results;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

         public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public ArrayList<Review> getResults() {
            return results;
        }

        public void setResults(ArrayList<Review> results) {
            this.results = results;
        }

        public int getTotal_pages() {
            return total_pages;
        }

        public void setTotal_pages(int total_pages) {
            this.total_pages = total_pages;
        }

        public int getTotal_results() {
            return total_results;
        }

        public void setTotal_results(int total_results) {
            this.total_results = total_results;
        }

        @Override
        public String toString() {
            return "Element{" +
                    "page=" + page +
                    ", results=" + results.toString() +
                    ", total_pages=" + total_pages +
                    ", total_results=" + total_results +
                    '}';
        }
}
