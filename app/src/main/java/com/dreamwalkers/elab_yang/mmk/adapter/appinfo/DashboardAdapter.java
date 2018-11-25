package com.dreamwalkers.elab_yang.mmk.adapter.appinfo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.model.Dashboard;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    private final List<Dashboard> mDataList;
    private DashboardClickListener mListener;
    private static int TYPE_FOOTER = 3;

    public DashboardAdapter(List<Dashboard> dataList) {
        mDataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        TimePoint card_view = mDataList.get(position);
        Dashboard item = mDataList.get(position);

        Log.d(TAG, "onBindViewHolder: type = " + item.getType());

        if (item.getType() == 1) {
//            Log.d(TAG, "onBindViewHolder: 1");
            holder.item_title.setText("알-림");
            holder.item_message.setText("장치를 추가하지않으셧군요!!!!???");
            holder.item_title.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.item_title.setBackgroundResource(R.color.ella_page_background);
            holder.item_message.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.item_message.setBackgroundResource(R.color.ella_page_background);
        } else if (item.getType() == 2) {
//            Log.d(TAG, "onBindViewHolder: 2");
            holder.item_title.setText("알-림");
            holder.item_message.setText("투약정보를 추가하지않으셧군요!!!!???");
            holder.item_title.setBackgroundResource(R.color.google_red);
            holder.item_title.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.item_message.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.item_message.setBackgroundResource(R.color.google_red);
        } else if (item.getType() == 3) {
//            Log.d(TAG, "onBindViewHolder: 3");
            holder.item_title.setText("알-림");
            holder.item_message.setText("개인정보를 추가하지않으셧군요!!!!???");
            holder.item_title.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.item_title.setBackgroundResource(R.color.google_green);
            holder.item_message.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            holder.item_message.setBackgroundResource(R.color.google_green);
        } else {
            holder.item_title.setText(item.getTitle());
            holder.item_message.setText(item.getMessage());

            // 설정 추가하러 가기
            holder.add_btn.setOnClickListener(v -> {
//            Snackbar.make(v, position + " 위치의 '+버튼' 클릭하셨습니다", 3000).show());

                // 임시 데이터 추가
                mListener.onAddItemClicked(position);
            });

        }


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
        int type;
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

    public void setOnClickListener(DashboardClickListener listener) {
        mListener = listener;
    }

    public interface DashboardClickListener {
        void onItemClicked(int position);

        void onAddItemClicked(int position);
    }
}