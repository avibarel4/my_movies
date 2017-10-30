package com.avi.mymovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avi.mymovies.App;
import com.avi.mymovies.ImageSize;
import com.avi.mymovies.R;
import com.avi.mymovies.Utils;
import com.avi.mymovies.network.objects.Movie;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avibarel on 29/10/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> implements View.OnClickListener {

    private List<Movie> mData;

    private OnMovieClickedCallback mListener;

    public MoviesAdapter(OnMovieClickedCallback listener) {
        mData = new ArrayList<>();

        mListener = listener;
    }

    public void setData(List<Movie> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mViewMain.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Movie movie = mData.get(position);

        holder.mViewMain.setTag(position);

        Glide.with(App.getContext())
                .load(Utils.getImageUrlBySize(movie.getImage(), ImageSize.w185))
                .fitCenter()
                .into(holder.mImagePoster);

        holder.mTextTitle.setText(movie.getTitle());
        holder.mTextYear.setText(movie.getReleaseDate());
        holder.mTextSummary.setText(movie.getOverview());
        holder.mTextRating.setText(String.valueOf(movie.getVoteAvg()));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onMovieClicked(mData.get((int) v.getTag()));
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private View mViewMain;
        private ImageView mImagePoster;
        private TextView mTextTitle;
        private TextView mTextYear;
        private TextView mTextSummary;
        private TextView mTextRating;

        public ViewHolder(View itemView) {
            super(itemView);

            mViewMain = itemView.findViewById(R.id.layout_main);
            mImagePoster = (ImageView) itemView.findViewById(R.id.image_movie);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
            mTextYear = (TextView) itemView.findViewById(R.id.text_year);
            mTextSummary = (TextView) itemView.findViewById(R.id.text_summary);
            mTextRating = (TextView) itemView.findViewById(R.id.text_rating);
        }
    }

    public interface OnMovieClickedCallback {
        void onMovieClicked(Movie movie);
    }

}
