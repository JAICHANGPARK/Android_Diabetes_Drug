package com.dreamwalkers.elab_yang.mmk.adapter.appinfo;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.model.Profile;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private final List<Profile> mDataList;
    private ProfileClickListener mListener;
    private static int TYPE_FOOTER = 3;

    public ProfileAdapter(List<Profile> dataList) {
        mDataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        TimePoint card_view = mDataList.get(position);
        Profile item = mDataList.get(position);

        holder.item_title.setText(item.getTitle());
        holder.item_message.setText(item.getMessage());

        // 설정 추가하러 가기
        holder.add_btn.setOnClickListener(v -> {
//            Snackbar.make(v, position + " 위치의 '+버튼' 클릭하셨습니다", 3000).show());

            // 임시 데이터 추가
            mListener.onAddItemClicked(position);
        });


        if (mListener != null) {
            final int pos = holder.getAdapterPosition();
            holder.itemView.setOnClickListener(view -> mListener.onItemClicked(pos));
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_title;
        TextView item_message;
        ImageView add_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            item_title = (TextView) itemView.findViewById(R.id.text_title);
            item_message = (TextView) itemView.findViewById(R.id.text_message);
            add_btn = (ImageView) itemView.findViewById(R.id.add_btn);
        }
    }

    public void setOnClickListener(ProfileClickListener listener) {
        mListener = listener;
    }

    public interface ProfileClickListener {
        void onItemClicked(int position);
        void onAddItemClicked(int position);
    }
}