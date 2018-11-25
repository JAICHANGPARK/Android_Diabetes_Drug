package com.dreamwalkers.elab_yang.mmk.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.model.needle.Drug;
import com.dreamwalkers.elab_yang.mmk.model.needle.Insulin;

import java.util.ArrayList;

public class UserDurgInfoAdapter extends RecyclerView.Adapter<UserDurgInfoAdapter.UserDrugInfoViewHolder> {

    Context context;
    ArrayList<Drug> drugArrayList = new ArrayList<>();


    public UserDurgInfoAdapter(Context context, ArrayList<Drug> drugArrayList) {
        this.context = context;
        this.drugArrayList = drugArrayList;
    }

    @NonNull
    @Override
    public UserDrugInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_drug_layout, viewGroup, false);
        return new UserDrugInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserDrugInfoViewHolder userDrugInfoViewHolder, int i) {
        int drugCount = drugArrayList.size();
        userDrugInfoViewHolder.time.setText(drugArrayList.get(i).getDateTime());
        ArrayList<Insulin> insulinArrayList = drugArrayList.get(i).getInsulinList();
        for (Insulin insulin : insulinArrayList){
            TextView drug = new TextView(context);
            drug.setText(insulin.getName());
            userDrugInfoViewHolder.layout.addView(drug);
            userDrugInfoViewHolder.unit.setText("투약 단위 : " + insulin.getUnit());
        }


    }

    @Override
    public int getItemCount() {
        return drugArrayList.size();
    }

    class UserDrugInfoViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layout;
        TextView time, unit;

        public UserDrugInfoViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = (LinearLayout)itemView.findViewById(R.id.drug_layout);
            time = (TextView)itemView.findViewById(R.id.time_text_view);
            unit = (TextView)itemView.findViewById(R.id.unit_text_view);
        }
    }
}
