package com.avi.mymovies.network;

/**
 * Created by avibarel on 29/10/2017.
 */

public class Response<T> {
    private T mData;
    private Object mExtra;

    public Response(T data, Object extra) {
        mData = data;
        mExtra = extra;
    }

    public T getData() {
        return mData;
    }

    public Object getExtra() {
        return mExtra;
    }
}
