package com.avi.mymovies.network.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by avibarel on 30/10/2017.
 */

public class Actor {

    @SerializedName("name")
    private String mName;

    @SerializedName("character")
    private String mCharecter;

    @SerializedName("gender")
    private int mGender;

    @SerializedName("profile_path")
    private String mProfilePath;

    public String getName() {
        return mName;
    }

    public String getCharecter() {
        return mCharecter;
    }

    public int getGender() {
        return mGender;
    }

    public String getProfilePath() {
        return mProfilePath;
    }
}
