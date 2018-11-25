package com.dreamwalkers.elab_yang.mmk.adapter.drugselect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamwalker.multiselection.adapter.BaseRightAdapter;
import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.activity.select.Callback;
import com.dreamwalkers.elab_yang.mmk.model.drugselect.Track;



public class RightAdapter extends BaseRightAdapter<Track, ViewHolder> {

    private final Callback callback;

    public RightAdapter(Callback callback) {
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drug_select_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        ViewHolder.bind(holder, getItemAt(position));

        holder.itemView.setOnClickListener(view -> {
            view.setPressed(true);
            view.postDelayed(() -> {
                view.setPressed(false);
                callback.onClick(holder.getAdapterPosition());
            }, 200);
        });
    }
}
