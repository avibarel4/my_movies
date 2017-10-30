package com.avi.mymovies.network.objects.responses;

import com.avi.mymovies.network.objects.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by avibarel on 29/10/2017.
 */

public class MoviesResponse {

    @SerializedName("page")
    private int mPage;

    @SerializedName("total_pages")
    private int mTotalPages;

    @SerializedName("results")
    private List<Movie> mMovies;

    public int getPage() {
        return mPage;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    public List<Movie> getMovies() {
        return mMovies;
    }
}
