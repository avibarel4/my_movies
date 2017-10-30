package com.avi.mymovies.network.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by avibarel on 29/10/2017.
 */

public class Movie implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeFloat(mVoteAvg);
        dest.writeString(mTitle);
        dest.writeString(mReleaseDate);
        dest.writeString(mOverview);
        dest.writeString(mPosterPath);
        dest.writeString(mBackdropPath);
    }

    protected Movie(Parcel in) {
        mId = in.readInt();
        mVoteAvg = in.readFloat();
        mTitle = in.readString();
        mReleaseDate = in.readString();
        mOverview = in.readString();
        mPosterPath = in.readString();
        mBackdropPath = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
