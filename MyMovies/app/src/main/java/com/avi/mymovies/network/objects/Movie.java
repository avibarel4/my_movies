package com.avi.mymovies.network.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by avibarel on 29/10/2017.
 */

public class Movie {

    @SerializedName("id")
    private int mId;

    @SerializedName("vote_average")
    private float mVoteAvg;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("release_date")
    private String mReleaseDate;

    @SerializedName("overview")
    private String mOverview;

    @SerializedName("poster_path")
    private String mPosterPath;

    @SerializedName("backdrop_path")
    private String mBackdropPath;

    public int getId() {
        return mId;
    }

    public float getVoteAvg() {
        return mVoteAvg;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getImage() {
        return mPosterPath == null ? mBackdropPath : mPosterPath;
    }
}
