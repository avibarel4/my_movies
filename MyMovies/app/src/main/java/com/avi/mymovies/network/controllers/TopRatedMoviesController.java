package com.avi.mymovies.network.controllers;

import android.util.Log;

import com.avi.mymovies.BuildConfig;
import com.avi.mymovies.enums.ResponseType;
import com.avi.mymovies.network.Response;
import com.avi.mymovies.network.UrlFactory;
import com.avi.mymovies.network.objects.responses.MoviesResponse;

import retrofit2.Call;

/**
 * Created by avibarel on 29/10/2017.
 */

public class TopRatedMoviesController extends AbsController<MoviesResponse> {

    private static final String TAG = TopRatedMoviesController.class.getSimpleName();

    private int mPage;

    public TopRatedMoviesController(int page) {
        mPage = page;
    }

    @Override
    public void start() {
        UrlFactory.API api = buildRetrofit().create(UrlFactory.API.class);
        mCall = api.getTopRatedMovies(BuildConfig.API_KEY, mPage);
        mCall.enqueue(this);

        Log.d(TAG, "Request: " + mCall.request().url());
    }

    @Override
    public void onResponse(Call<MoviesResponse> call, retrofit2.Response<MoviesResponse> response) {

        if (mListener != null) {
            if (response == null || response.body() == null) {
                mListener.onError(new Throwable("Failed to get response"));
            } else {
                ResponseType responseType = mPage == 1 ? ResponseType.TOP_RATED_MOVIES_FETCH_NEW : ResponseType.TOP_RATED_MOVIES_NEXT_PAGE;
                mListener.onSuccess(new Response<>(response.body(), responseType, getExtra()));
            }
        }

        super.onResponse(call, response);
    }

    @Override
    public void onFailure(Call<MoviesResponse> call, Throwable t) {

        if (mListener != null) {
            mListener.onError(t);
        }

        super.onFailure(call, t);
    }
}
