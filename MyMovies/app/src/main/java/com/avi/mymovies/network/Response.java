package com.avi.mymovies.network;

import com.avi.mymovies.enums.ResponseType;

/**
 * Created by avibarel on 29/10/2017.
 */

public class Response<T> {
    private T mData;
    private ResponseType mResponseType;
    private Object mExtra;

    public Response(T data, ResponseType responseType, Object extra) {
        mData = data;
        mResponseType = responseType;
        mExtra = extra;
    }

    public T getData() {
        return mData;
    }

    public ResponseType getResponseType() {
        return mResponseType;
    }

    public Object getExtra() {
        return mExtra;
    }
}
