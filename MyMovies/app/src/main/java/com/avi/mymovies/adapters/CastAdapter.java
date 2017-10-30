package com.avi.mymovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avi.mymovies.App;
import com.avi.mymovies.R;
import com.avi.mymovies.Utils;
import com.avi.mymovies.enums.ImageSize;
import com.avi.mymovies.network.objects.Actor;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avibarel on 29/10/2017.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> implements View.OnClickListener {

    private List<Actor> mData;

    private OnActorClickedCallback mListener;

    public CastAdapter() {
        mData = new ArrayList<>();
    }

    public void setData(List<Actor> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void setListener(OnActorClickedCallback listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_actor, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mViewMain.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Actor actor = mData.get(position);

        holder.mViewMain.setTag(position);

        Glide.with(App.getContext())
                .load(Utils.getImageUrlBySize(actor.getProfilePath(), ImageSize.w154))
                .centerCrop()
                .thumbnail(Glide.with(App.getContext()).load(R.drawable.actor).centerCrop())
                .into(holder.mImageActor);

        holder.mTextName.setText(actor.getName());
        holder.mTextCharacter.setText(actor.getCharecter());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onActorClicked(mData.get((int) v.getTag()));
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private View mViewMain;
        private ImageView mImageActor;
        private TextView mTextName;
        private TextView mTextCharacter;

        public ViewHolder(View itemView) {
            super(itemView);

            mViewMain = itemView.findViewById(R.id.layout_main);
            mImageActor = (ImageView) itemView.findViewById(R.id.image_actor);
            mTextName = (TextView) itemView.findViewById(R.id.text_name);
            mTextCharacter = (TextView) itemView.findViewById(R.id.text_character);
        }
    }

    public interface OnActorClickedCallback {
        void onActorClicked(Actor actor);
    }

}
