package com.avi.mymovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.avi.mymovies.fragments.TopRatedMoviesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.layout_container, TopRatedMoviesFragment.newInstance())
                    .commit();
        }
    }
}
