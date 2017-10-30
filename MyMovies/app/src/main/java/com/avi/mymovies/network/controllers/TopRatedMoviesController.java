package com.avi.mymovies.network.controllers;

import com.avi.mymovies.BuildConfig;
import com.avi.mymovies.network.Response;
import com.avi.mymovies.network.UrlFactory;
import com.avi.mymovies.network.objects.responses.MoviesResponse;

import retrofit2.Call;

/**
 * Created by avibarel on 29/10/2017.
 */

public class TopRatedMoviesController extends AbsController<MoviesResponse> {

    private int mPage;

    public TopRatedMoviesController(int page) {
        mPage = page;
    }

    @Override
    public void start() {
        UrlFactory.API api = buildRetrofit().create(UrlFactory.API.class);
        mCall = api.getTopRatedMovies(BuildConfig.API_KEY, mPage);
        mCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<MoviesResponse> call, retrofit2.Response<MoviesResponse> response) {

        if (mListener != null) {
            if (response == null || response.body() == null) {
                mListener.onError(new Throwable("Failed to get response"));
            } else {
                mListener.onSuccess(new Response<>(response.body(), getExtra()));
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
