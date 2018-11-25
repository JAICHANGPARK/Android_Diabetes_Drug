/*  Copyright (C) 2015-2018 Andreas Shimokawa, Carsten Pfeiffer, Daniele
    Gobbetti, Lem Dulfo

    This file is part of Gadgetbridge.

    Gadgetbridge is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Gadgetbridge is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>. */
package com.dreamwalkers.elab_yang.mmk.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.activity.DeviceControlActivity;
import com.dreamwalkers.elab_yang.mmk.activity.navi.AlyakActivity;
import com.dreamwalkers.elab_yang.mmk.activity.navi.OneInsulinActivity;
import com.dreamwalkers.elab_yang.mmk.activity.navi.TwoInsulinActivity;
import com.dreamwalkers.elab_yang.mmk.activity.sync.needle.v1.DataSyncActivity;
import com.dreamwalkers.elab_yang.mmk.model.Device;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.paperdb.Paper;

import static android.content.Context.MODE_PRIVATE;
import static com.dreamwalkers.elab_yang.mmk.consts.IntentConst.DEVICEADDRESS;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private final Context context;
    private List<Device> deviceList;
    private int expandedDevicePosition = RecyclerView.NO_POSITION;
    private ViewGroup parent;
    private static final String TAG = "DeviceAdapter";
    boolean deviceFlag;
    HashSet<Device> deviceDatabase = new HashSet<>();
    ArrayList<Device> deviceArrayList;

    // 투약 갯수 플래그
    int needle_cnt_flag = 0;

    public DeviceAdapter(Context context, List<Device> deviceList) {
        this.context = context;
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Paper.init(context);
        this.parent = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        String deviceName = deviceList.get(position).getDeviceName();
        String deviceAddress = deviceList.get(position).getDeviceAddress();
        holder.deviceNameLabel.setText(deviceList.get(position).getDeviceName());
        if (deviceList.get(position).getDeviceName().equals("NeedleBT")) {
            Log.d(TAG, "onBindViewHolder: 니들맞네");
            holder.deviceNameLabel.setText("니들");
        } else {
            Log.d(TAG, "onBindViewHolder: 전용니들이 아니네");
            holder.deviceNameLabel.setText(deviceList.get(position).getDeviceName());
        }
        holder.deviceStatusLabel.setText(deviceList.get(position).getDeviceAddress());

        // 동기화 -> 로티
//        holder.showActivityTracks.setOnClickListener((View v) -> {
//            SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
//            String only_one_needle = "";
//            String only_one_needle_data = "";
//            String two_needle1 = "";
//            String two_needle2 = "";
//            String two_needle3 = "";
//            String two_needle4 = "";
//            String two_needle1_data = "";
//            String two_needle2_data = "";
//            String two_needle3_data = "";
//            String two_needle4_data = "";
//
//            only_one_needle_data = pref.getString("SET_DATA", only_one_needle);
//
//            two_needle1_data = pref.getString("cache_data_1", two_needle1);
//            two_needle2_data = pref.getString("cache_data_2", two_needle2);
//            two_needle3_data = pref.getString("cache_data_3", two_needle3);
//            two_needle4_data = pref.getString("cache_data_4", two_needle4);
//
//
//            if (only_one_needle_data.equals("") && two_needle1_data.equals("")
//                    && two_needle2_data.equals("") && two_needle3_data.equals("") && two_needle4_data.equals("")) {
//                // 다 공백이면 설정을 안한거야
//                Log.d(TAG, "onBindViewHolder: 설정값 없음");
//                Snackbar.make(v, "인슐린을 설정해주세요", 3000).setAction("YES", v1 -> {
//                    onCreateDialog();
//                }).show();
//
//            } else if (only_one_needle_data.equals("") && (two_needle1_data != "" || two_needle2_data != "" || two_needle3_data != "" || two_needle4_data != "")) {
//                // 2개네 \\\
//                needle_cnt_flag = 2;
//                Log.d(TAG, "onBindViewHolder: 2개 사용하네");
////                Snackbar.make(v, "인슐린 2개 사용", 3000).setAction("YES", v1 -> {
////                }).show();
//                Intent intent = new Intent(context, DeviceControlActivity.class);
//                intent.putExtra(DEVICEADDRESS, deviceAddress);
//                context.startActivity(intent);
//            } else {
//                needle_cnt_flag = 1;
//                Log.d(TAG, "onBindViewHolder: 1개 사용하네");
////                Snackbar.make(v, "인슐린 1개 사용", 3000).setAction("YES", v1 -> {
////                }).show();
//
//                Intent intent = new Intent(context, DeviceControlActivity.class);
//                intent.putExtra(DEVICEADDRESS, deviceAddress);
//                context.startActivity(intent);
//            }
//
//            // 인텐트
////            Intent intent = new Intent(context, DeviceControlActivity.class);
////            intent.putExtra(DEVICEADDRESS, deviceAddress);
////            // needle_cnt_flag = 1 : 1개 사용
////            // needle_cnt_flag = 2 : 2개 사용
////            context.startActivity(intent);
//
////            if (AAAA.equals("")) {
////                Log.d(TAG, "onBindViewHolder: 설정부터해");
//////                Snackbar.make(v, "설정부터 해주세요", Snackbar.LENGTH_LONG).show();
////                Snackbar.make(v, "설정부터 해주세요", 3000).setAction("YES", v1 -> {
////                    // 할거
////                }).show();
////            } else {
////                Log.d(TAG, "onBindViewHolder: 설정미리햇구나?");
////                Intent intent = new Intent(context, DeviceControlActivity.class);
////                intent.putExtra(DEVICEADDRESS, deviceAddress);
////                context.startActivity(intent);
////            }
//        });

        // 서비스 연결
        holder.fetchActivityData.setOnClickListener(v -> {
//            Intent intent = new Intent(context, AutoReceiveActivity.class);
            Intent intent = new Intent(context, DataSyncActivity.class);
            intent.putExtra(DEVICEADDRESS, deviceAddress);
            intent.putExtra("needle_cnt_flag", needle_cnt_flag);
            context.startActivity(intent);
        });

        // 연결장치 제거
        holder.deviceRemove.setOnClickListener(v -> {
            Log.e(TAG, "deviceAddress: --> " + deviceAddress);
            Log.e(TAG, "deviceName: --> " + deviceName);
            deviceDatabase = Paper.book("device").read("user_device");
            deviceArrayList = new ArrayList<>(deviceDatabase);
            for (Device d : deviceArrayList) {
                Log.e(TAG, "deviceArrayList: --> " + d.getDeviceAddress());
                Log.e(TAG, "deviceArrayList: --> " + d.getDeviceName());
            }
            Log.e(TAG, "onBindViewHolder: " + deviceList.get(position).getDeviceName());
            Log.e(TAG, "onBindViewHolder: " + deviceList.indexOf(deviceList.get(position)));
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("장비 삭제하기");
            builder.setMessage("등록하신 장비를 삭제하시겠어요?");
            builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                Log.e(TAG, "onClick: " + deviceAddress);
                deviceList.remove(deviceList.get(position));
                HashSet<Device> tmpSet = new HashSet<>(deviceList);
                for (Device d : tmpSet) {
                    Log.e(TAG, "tmpSet: --> " + d.getDeviceAddress());
                    Log.e(TAG, "tmpSet: --> " + d.getDeviceName());
                }
                Paper.book("device").delete("user_device");
                Paper.book("device").write("user_device", tmpSet);
                notifyDataSetChanged();
            });
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardview;
        TextView deviceNameLabel;
        TextView deviceStatusLabel;
        TextView fetchActivityData;
//        TextView showActivityTracks;
        ImageView deviceRemove;

        ViewHolder(View view) {
            super(view);
            cardview = view.findViewById(R.id.card_view);
            deviceNameLabel = view.findViewById(R.id.device_name);
            deviceStatusLabel = view.findViewById(R.id.device_status);
            fetchActivityData = view.findViewById(R.id.device_action_fetch_activity);
//            showActivityTracks = view.findViewById(R.id.device_action_show_activity_tracks);
            deviceRemove = view.findViewById(R.id.device_info_trashcan);
        }
    }

    private void onCreateDialog() {
        final String[] items = {"인슐린 1개", "인슐린 2개", "알ㅡ약"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("선택해")
//                .setCancelable(false)
                .setSingleChoiceItems(items, 0, (DialogInterface dialog, int which) -> {
                    Toast.makeText(context, items[which], Toast.LENGTH_SHORT).show();
                    if (which == 0) {
                        // 약 1개
                        context.startActivity(new Intent(context, OneInsulinActivity.class));
                    } else if (which == 1) {
                        // 약 2개
                        context.startActivity(new Intent(context, TwoInsulinActivity.class));
                    } else {
                        // 알약
                        context.startActivity(new Intent(context, AlyakActivity.class));

                    }
                    dialog.dismiss(); // 누르면 바로 닫히는 형태
                })
                .show();
    }
}