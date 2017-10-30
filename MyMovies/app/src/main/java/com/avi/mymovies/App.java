package com.avi.mymovies;

import android.app.Application;
import android.content.Context;

/**
 * Created by avibarel on 29/10/2017.
 */

public class App extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
