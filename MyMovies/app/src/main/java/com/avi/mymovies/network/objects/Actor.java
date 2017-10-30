package com.avi.mymovies.network.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by avibarel on 30/10/2017.
 */

public class Actor implements Parcelable {

    @SerializedName("name")
    private String mName;

    @SerializedName("character")
    private String mCharecter;

    @SerializedName("profile_path")
    private String mProfilePath;

    public String getName() {
        return mName;
    }

    public String getCharecter() {
        return mCharecter;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mCharecter);
        dest.writeString(mProfilePath);
    }

    protected Actor(Parcel in) {
        mName = in.readString();
        mCharecter = in.readString();
        mProfilePath = in.readString();
    }

    public static final Creator<Actor> CREATOR = new Creator<Actor>() {
        @Override
        public Actor createFromParcel(Parcel in) {
            return new Actor(in);
        }

        @Override
        public Actor[] newArray(int size) {
            return new Actor[size];
        }
    };
}
