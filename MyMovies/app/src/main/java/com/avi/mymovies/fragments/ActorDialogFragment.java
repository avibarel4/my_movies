package com.avi.mymovies.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by avibarel on 30/10/2017.
 */

public class ActorDialogFragment extends DialogFragment {

    private static final String EXTRA_KEY_ACTOR = "extra_key_actor";

    public static ActorDialogFragment newInstance(Actor actor) {

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_KEY_ACTOR, actor);

        ActorDialogFragment fragment = new ActorDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Actor actor = getArguments().getParcelable(EXTRA_KEY_ACTOR);

        View view = inflater.inflate(R.layout.dialog_fragment_actor, container, false);

        ImageView imageActor = (ImageView) view.findViewById(R.id.image_actor);

        Glide.with(App.getContext())
                .load(Utils.getImageUrlBySize(actor.getProfilePath(), ImageSize.w154))
                .thumbnail(Glide.with(App.getContext()).load(R.drawable.actor).centerCrop())
                .centerCrop()
                .into(imageActor);

        ((TextView) view.findViewById(R.id.text_name)).setText(actor.getName());

        return view;
    }
}
