package com.avi.mymovies.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avi.mymovies.R;
import com.avi.mymovies.Utils;
import com.avi.mymovies.adapters.CastAdapter;
import com.avi.mymovies.enums.ImageSize;
import com.avi.mymovies.network.IResponse;
import com.avi.mymovies.network.Response;
import com.avi.mymovies.network.controllers.GetMovieCastController;
import com.avi.mymovies.network.objects.Actor;
import com.avi.mymovies.network.objects.Movie;
import com.avi.mymovies.network.objects.responses.MovieCastResponse;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avibarel on 30/10/2017.
 */
public class MovieFragment extends Fragment implements IResponse<Response<MovieCastResponse>>,CastAdapter.OnActorClickedCallback {

    private final static String EXTRA_KEY_MOVIE = "extra_key_movie";

    private RecyclerView mRecyclerCast;
    private CastAdapter mCastAdapter;

    private List<Actor> mCast;

    public static MovieFragment newInstance(Movie movie) {

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_KEY_MOVIE, movie);

        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Movie movie = getArguments().getParcelable(EXTRA_KEY_MOVIE);

        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        mRecyclerCast = (RecyclerView) view.findViewById(R.id.recycler_cast);
        mRecyclerCast.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mCastAdapter = new CastAdapter();
        mRecyclerCast.setAdapter(mCastAdapter);

        ImageView imageMovie = (ImageView) view.findViewById(R.id.image_movie);
        TextView txtTitle = (TextView) view.findViewById(R.id.text_title);
        TextView txtYear = (TextView) view.findViewById(R.id.text_year);
        TextView txtRating = (TextView) view.findViewById(R.id.text_rating);
        TextView txtSummary = (TextView) view.findViewById(R.id.text_summary);

        Glide.with(getActivity())
                .load(Utils.getImageUrlBySize(movie.getImage(), ImageSize.w780))
                .centerCrop()
                .into(imageMovie);

        txtTitle.setText(movie.getTitle());
        txtYear.setText(movie.getYear());
        txtRating.setText(String.valueOf(movie.getVoteAvg()));
        txtSummary.setText(movie.getOverview());

        // fetch cast
        new GetMovieCastController(movie.getId())
            .setListener(this)
            .start();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mCastAdapter.setListener(this);

        setCastData();
    }

    @Override
    public void onPause() {
        super.onPause();

        mCastAdapter.setListener(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mRecyclerCast.setAdapter(null);
        mRecyclerCast.setLayoutManager(null);
        mRecyclerCast = null;
        mCastAdapter = null;
    }

    @Override
    public void onSuccess(Response<MovieCastResponse> result) {
        mCast = result.getData().getCast();
        setCastData();
    }

    @Override
    public void onError(Throwable t) {
        mCast = new ArrayList<>();
        setCastData();
    }

    private void setCastData() {
        if (!isAdded() || mCast == null) {
            return;
        }

        mCastAdapter.setData(mCast);
    }

    @Override
    public void onActorClicked(Actor actor) {
        if (isAdded()) {
            ActorDialogFragment.newInstance(actor).show(getChildFragmentManager(), null);
        }
    }
}
