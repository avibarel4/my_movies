package com.avi.mymovies.network.controllers;

import android.support.annotation.CallSuper;

import com.avi.mymovies.network.IResponse;
import com.avi.mymovies.network.Response;
import com.avi.mymovies.network.UrlFactory;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by avibarel on 29/10/2017.
 */

public abstract class AbsController<T> implements Callback<T> {

    protected IResponse<Response<T>> mListener;
    protected Call<T> mCall;

    private Object mExtra;

    public abstract void start();

    private static Retrofit sRetrofit;

    static {
        sRetrofit = new Retrofit.Builder()
                .baseUrl(UrlFactory.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
    }

    protected Retrofit buildRetrofit() {
        return sRetrofit;
    }

    protected Object getExtra() {
        return mExtra;
    }

    public AbsController<T> setExtra(Object extra) {
        mExtra = extra;
        return this;
    }

    public final AbsController<T> setListener(IResponse listener) {
        mListener = listener;
        return this;
    }

    public final void cancel() {
        mListener = null;

        if (mCall != null) {
            mCall.cancel();
        }
        mCall = null;
    }

    /**
     * Override methods in subclasses should call the super method at the end of their work
     *
     * @param call
     * @param response
     */
    @Override
    @CallSuper
    public void onResponse(Call<T> call, retrofit2.Response<T> response) {
        mListener = null;
        mCall = null;
    }

    /**
     * Override methods in subclasses should call the super method at the end of their work
     *
     * @param call
     * @param t
     */
    @Override
    @CallSuper
    public void onFailure(Call<T> call, Throwable t) {
        mListener = null;
        mCall = null;
    }

}
