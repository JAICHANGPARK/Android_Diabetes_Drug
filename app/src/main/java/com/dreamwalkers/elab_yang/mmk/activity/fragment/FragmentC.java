package com.dreamwalkers.elab_yang.mmk.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.activity.IActivityBased;
import com.dreamwalkers.elab_yang.mmk.adapter.DeviceAdapter;
import com.dreamwalkers.elab_yang.mmk.model.Device;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

// 장치를 추가하고 데이터를 받습니다!
public class FragmentC extends Fragment {
    private static final String TAG = "FragmentC";

    //    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    DeviceAdapter deviceAdapter;
    HashSet<Device> deviceDatabase = new HashSet<>();
    ArrayList<Device> deviceArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);
        // TODO: 2018-11-12 view. 없이 버터나이프로 걸면 왜 오류가 뜰까요?
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        setDevice();
        return view;
    }

    public void setDevice() {
//        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        deviceDatabase = Paper.book("device").read("user_device");
        if (deviceDatabase != null) {
            if (deviceDatabase.size() != 0) {
                deviceArrayList = new ArrayList<>(deviceDatabase);
                deviceAdapter = new DeviceAdapter(getContext(), deviceArrayList);
                recyclerView.setAdapter(deviceAdapter);
                for (int i = 0; i < deviceArrayList.size(); i++) {
                    Device device = deviceArrayList.get(i);
                }
            }
        } else {
            Log.d(TAG, "setDevice: 장치x");
        }
    }
}
