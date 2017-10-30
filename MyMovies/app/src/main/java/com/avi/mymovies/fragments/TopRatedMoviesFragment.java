package com.avi.mymovies.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avi.mymovies.R;
import com.avi.mymovies.adapters.MoviesAdapter;
import com.avi.mymovies.network.IResponse;
import com.avi.mymovies.network.Response;
import com.avi.mymovies.network.controllers.TopRatedMoviesController;
import com.avi.mymovies.network.objects.Movie;
import com.avi.mymovies.network.objects.responses.MoviesResponse;

/**
 * Created by avibarel on 29/10/2017.
 */

public class TopRatedMoviesFragment extends Fragment implements MoviesAdapter.OnMovieClickedCallback, IResponse<Response<MoviesResponse>> {

    private MoviesAdapter mMoviesAdapter;

    public static TopRatedMoviesFragment newInstance() {
        Bundle args = new Bundle();

        TopRatedMoviesFragment fragment = new TopRatedMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_rated_movies, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mMoviesAdapter = new MoviesAdapter(this);
        recyclerView.setAdapter(mMoviesAdapter);

        new TopRatedMoviesController(1).setListener(this).start();

        return view;
    }

    @Override
    public void onMovieClicked(Movie movie) {

    }

    @Override
    public void onSuccess(Response<MoviesResponse> result) {
        if (isAdded()) {
            mMoviesAdapter.setData(result.getData().getMovies());
        }
    }

    @Override
    public void onError(Throwable t) {

    }
}
