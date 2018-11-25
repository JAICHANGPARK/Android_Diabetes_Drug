package com.dreamwalkers.elab_yang.mmk.adapter.appinfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.model.TimePoint;

import java.util.List;

import static android.content.ContentValues.TAG;

public class TimePointAdapter extends RecyclerView.Adapter<TimePointAdapter.ViewHolder> {
    Context context;
    private final List<TimePoint> mDataList;
    private TimePointClickListener mListener;
    private static int TYPE_FOOTER = 4;

    public TimePointAdapter(Context context, List<TimePoint> dataList) {
        this.context = context;
        mDataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timepoint_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        TimePoint card_view = mDataList.get(position);
        TimePoint item = mDataList.get(position);

        // detail하게 보기, 확대용
        holder.card_view.setOnClickListener((View v) -> {
//            Snackbar.make(v, position + "번 째 카드뷰 클릭하셨습니다", 1000).show();

            // 다이얼로그로 삭제하시겠습니까? 표시
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder
                    .setTitle("title. 삭제")
                    .setMessage("message. 삭제")
                    .setPositiveButton("yes",
                            (dialog, id) -> {
                                // 삭제
                                removeAt(position);
                                dialog.dismiss();
                            })
                    .setNegativeButton("no",
                            (dialog, id) -> {
                                // 안삭제
                                dialog.dismiss();
                            });
            builder.create()
                    .show();
        });


        holder.item_timepoint.setText(item.getTimepoint());
        holder.item_name.setText(item.getName());
        holder.item_unit.setText(item.getUnit());
        holder.item_unit.setText(item.getUnit());

        // 단위칸이 비어있다면!? hint = "단위입력"
        if (holder.item_unit.getText().equals("")) {
//            holder.item_unit.setBackgroundResource(R.color.red);
            holder.item_unit.setHint("단위 입력");
        }

        holder.item_unit.setOnClickListener(v -> {
            Log.d(TAG, "onBindViewHolder: item.getName() = " + item.getName());

            // TODO: 2018-11-20 설정 단계별로 묶어야하지만 우선은 품명을 설정안하면 단위를 못설정하도록 잠궈놈
            if (holder.item_name.getText().equals("")) {
                Snackbar.make(v, "설정먼저ㄱ", Snackbar.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "onBindViewHolder: item.getTimepoint() = " + item.getTimepoint());
                // 다이얼로그
                final EditText et = new EditText(context);
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog.Builder builder13 = new AlertDialog.Builder(context)
                        .setTitle("단위")
                        .setPositiveButton("yes", (DialogInterface dialog, int id) -> {
                            item.setUnit(et.getText().toString());
                            holder.item_unit.setText(item.getUnit());
                        })
                        .setView(et);
                builder13.create()
                        .show();
            }
        });

//         품명과 단위 추가하러 가기
        holder.add_btn.setOnClickListener(v -> {
            // 임시 데이터 추가
            mListener.onAddItemClicked(position, item.getTimepoint());
//            context.startActivity(new Intent(context, SelectDrugActivity.class));
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

    public void removeAt(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataList.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView card_view;
        TextView item_timepoint;
        TextView item_name;
        TextView item_unit;
        ImageView add_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            item_timepoint = (TextView) itemView.findViewById(R.id.item_timepoint);
            item_name = (TextView) itemView.findViewById(R.id.item_name);
            item_unit = (TextView) itemView.findViewById(R.id.item_unit);
            add_btn = (ImageView) itemView.findViewById(R.id.add_btn);
        }
    }

    public void setOnClickListener(TimePointClickListener listener) {
        mListener = listener;
    }

    public interface TimePointClickListener {
        void onItemClicked(int position);

        void onAddItemClicked(int position, String item_timepoint);
    }
}
