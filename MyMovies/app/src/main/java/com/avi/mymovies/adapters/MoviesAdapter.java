package com.avi.mymovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.avi.mymovies.App;
import com.avi.mymovies.enums.ImageSize;
import com.avi.mymovies.R;
import com.avi.mymovies.Utils;
import com.avi.mymovies.network.objects.Movie;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avibarel on 29/10/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> implements View.OnClickListener, Filterable {

    private List<Movie> mAllData;
    private List<Movie> mFilteredData;

    private OnMovieClickedCallback mListener;

    public MoviesAdapter() {
        mAllData = new ArrayList<>();
        mFilteredData = new ArrayList<>();
    }

    public void setData(List<Movie> data) {
        mAllData = data;
        mFilteredData = data;
        notifyDataSetChanged();
    }

    public void setListener(OnMovieClickedCallback listener) {
        mListener = listener;
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

        Movie movie = mFilteredData.get(position);

        holder.mViewMain.setTag(position);

        Glide.with(App.getContext())
                .load(Utils.getImageUrlBySize(movie.getImage(), ImageSize.w185))
                .fitCenter()
                .thumbnail(Glide.with(App.getContext()).load(R.drawable.movie_image).fitCenter())
                .into(holder.mImagePoster);

        holder.mTextTitle.setText(movie.getTitle());
        holder.mTextYear.setText(movie.getYear());
        holder.mTextSummary.setText(movie.getOverview());
        holder.mTextRating.setText(String.valueOf(movie.getVoteAvg()));

    }

    @Override
    public int getItemCount() {
        return mFilteredData.size();
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onMovieClicked(mFilteredData.get((int) v.getTag()));
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String text = charSequence.toString();

                if (TextUtils.isEmpty(text)) {
                    mFilteredData = mAllData;
                } else {
                    mFilteredData = new ArrayList<>();

                    for (Movie movie : mAllData) {
                        if (movie.getTitle().toLowerCase().contains(text.toLowerCase())) {
                            mFilteredData.add(movie);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredData;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredData = (List<Movie>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnMovieClickedCallback {
        void onMovieClicked(Movie movie);
    }

}
