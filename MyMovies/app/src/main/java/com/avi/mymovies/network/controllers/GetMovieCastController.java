package com.avi.mymovies.network.controllers;

import android.util.Log;

import com.avi.mymovies.BuildConfig;
import com.avi.mymovies.enums.ResponseType;
import com.avi.mymovies.network.UrlFactory;
import com.avi.mymovies.network.objects.responses.MovieCastResponse;

import retrofit2.Call;

/**
 * Created by avibarel on 30/10/2017.
 */

public class GetMovieCastController extends AbsController<MovieCastResponse> {

    private static final String TAG = GetMovieCastController.class.getSimpleName();

    private int mMovieId;

    public GetMovieCastController(int movieId) {
        mMovieId = movieId;
    }

    @Override
    public void start() {
        UrlFactory.API api = buildRetrofit().create(UrlFactory.API.class);
        mCall = api.getMovieCast(mMovieId, BuildConfig.API_KEY);
        mCall.enqueue(this);

        Log.d(TAG, "Request: " + mCall.request().url());
    }

    @Override
    public void onResponse(Call<MovieCastResponse> call, retrofit2.Response<MovieCastResponse> response) {

        if (mListener != null) {
            if (response == null || response.body() == null) {
                mListener.onError(new Throwable("Failed to get response"));
            } else {
                mListener.onSuccess(new com.avi.mymovies.network.Response<>(response.body(), ResponseType.MOVIE_CAST, getExtra()));
            }
        }

        super.onResponse(call, response);
    }

    @Override
    public void onFailure(Call<MovieCastResponse> call, Throwable t) {

        if (mListener != null) {
            mListener.onError(t);
        }

        super.onFailure(call, t);
    }
}
