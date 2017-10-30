package com.avi.mymovies.network.objects.responses;

import com.avi.mymovies.network.objects.Actor;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by avibarel on 30/10/2017.
 */

public class MovieCastResponse {

    @SerializedName("cast")
    private List<Actor> mCast;

    public List<Actor> getCast() {
        return mCast;
    }
}
