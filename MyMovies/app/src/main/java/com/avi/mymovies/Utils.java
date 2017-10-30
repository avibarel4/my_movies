package com.avi.mymovies;

import android.support.annotation.NonNull;

import com.avi.mymovies.network.UrlFactory;

/**
 * Created by avibarel on 29/10/2017.
 */

public class Utils {

    private Utils() {
        // private constructor
    }

    public static String getImageUrlBySize(String image, @NonNull ImageSize imageSize) {
        return UrlFactory.BASE_IMAGE_URL + imageSize + image;
    }
}
