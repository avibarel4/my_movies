package com.avi.mymovies.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avi.mymovies.MainActivity;
import com.avi.mymovies.R;
import com.avi.mymovies.adapters.MoviesAdapter;
import com.avi.mymovies.network.IResponse;
import com.avi.mymovies.network.Response;
import com.avi.mymovies.network.controllers.TopRatedMoviesController;
import com.avi.mymovies.network.objects.Movie;
import com.avi.mymovies.network.objects.responses.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avibarel on 29/10/2017.
 */

public class TopRatedMoviesFragment extends Fragment implements MoviesAdapter.OnMovieClickedCallback, IResponse<Response<MoviesResponse>>, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = TopRatedMoviesFragment.class.getSimpleName();

    private List<Movie> mMovies;

    private RecyclerView mRecycler;
    private LinearLayoutManager mLinearLayoutManager;
    private MoviesAdapter mMoviesAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean mDataInProgress = false;
    private boolean mReachedLimit = false;

    private int mCurrentPage = 0;

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

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe_refresh);

        mRecycler = (RecyclerView) view.findViewById(R.id.recycler_movies);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLinearLayoutManager);

        mMoviesAdapter = new MoviesAdapter();
        mRecycler.setAdapter(mMoviesAdapter);

        fetchData(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mMoviesAdapter.setListener(this);

        mRecycler.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void onPause() {
        super.onPause();

        mSwipeRefreshLayout.setOnRefreshListener(null);
        mMoviesAdapter.setListener(null);

        mRecycler.removeOnScrollListener(mOnScrollListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mSwipeRefreshLayout = null;
        mMoviesAdapter = null;

        mRecycler.setAdapter(null);
        mRecycler.setLayoutManager(null);

        mRecycler = null;
        mMovies = null;
    }

    @Override
    public void onSuccess(Response<MoviesResponse> result) {

        MoviesResponse moviesResponse = result.getData();

        mReachedLimit = mCurrentPage == moviesResponse.getTotalPages();

        switch (result.getResponseType()) {
            case TOP_RATED_MOVIES_FETCH_NEW:
                mMovies = moviesResponse.getMovies();
                break;
            case TOP_RATED_MOVIES_NEXT_PAGE:
                if (mMovies == null) {
                    mMovies = moviesResponse.getMovies();
                } else {
                    mMovies.addAll(moviesResponse.getMovies());
                }
                break;
        }

        Log.d(TAG, "New Data for Page: " + moviesResponse.getPage());

        if (mMovies == null) {
            mMovies = new ArrayList<>();
        }
        updateData();
    }

    @Override
    public void onError(Throwable t) {
        mMovies = new ArrayList<>();
        updateData();
    }

    private void updateData() {

        mDataInProgress = false;

        if (isAdded()) {
            boolean displayProgress;

            if (mMovies == null) {
                // still fetching?
                displayProgress = true;
            } else {
                displayProgress = false;
                mMoviesAdapter.setData(mMovies);
            }

            mSwipeRefreshLayout.setRefreshing(displayProgress);
        }
    }

    @Override
    public void onMovieClicked(Movie movie) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).openMoviePage(movie);
        }
    }

    @Override
    public void onRefresh() {
        fetchData(true); // get new data
    }

    private void fetchData(boolean fetchNew) {
        if (!mDataInProgress) {
            mDataInProgress = true;

            if (fetchNew) {
                mCurrentPage = 0; // reset
            }

            mCurrentPage++;
            new TopRatedMoviesController(mCurrentPage).setListener(this).start();

            Log.d(TAG, "Get New Data for Page " + mCurrentPage);
        }
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int totalItemCount = mLinearLayoutManager.getItemCount();

            boolean shouldGetMore = !mDataInProgress && !mReachedLimit;

            if (shouldGetMore) {
                int visibleItemCount = mLinearLayoutManager.getChildCount();
                int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();

                if (firstVisibleItemPosition >= 0 && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 3) { // load 3 before the end
                    fetchData(false);
                }
            }
        }
    };
}
