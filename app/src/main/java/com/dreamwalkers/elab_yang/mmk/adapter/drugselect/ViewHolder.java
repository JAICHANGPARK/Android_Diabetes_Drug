package com.dreamwalkers.elab_yang.mmk.adapter.drugselect;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.model.drugselect.Track;


/**
 * Created by Artem Kholodnyi on 9/6/16.
 */
class ViewHolder extends RecyclerView.ViewHolder {
    TextView track;
    TextView artist;
    ImageView avatar;

    public ViewHolder(View view) {
        super(view);
        track = (TextView) view.findViewById(R.id.track);
        artist = (TextView) view.findViewById(R.id.artist);
        avatar = (ImageView) view.findViewById(R.id.yal_ms_avatar);
    }

    public static void bind(ViewHolder viewHolder, Track track) {
        viewHolder.track.setText(track.getTrackName());
        viewHolder.artist.setText(track.getArtist());
        viewHolder.avatar.setImageResource(track.getAlbum());
    }
}
