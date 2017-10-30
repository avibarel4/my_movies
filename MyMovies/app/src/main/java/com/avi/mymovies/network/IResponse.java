package com.avi.mymovies.network;

/**
 * Created by avibarel on 29/10/2017.
 */

public interface IResponse<T> {
    void onSuccess(T result);
    void onError(Throwable t);
}
