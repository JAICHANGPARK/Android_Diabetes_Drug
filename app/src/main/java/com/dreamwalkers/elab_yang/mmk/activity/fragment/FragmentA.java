package com.dreamwalkers.elab_yang.mmk.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.adapter.appinfo.DashboardAdapter;
import com.dreamwalkers.elab_yang.mmk.model.Dashboard;

import java.util.ArrayList;
import java.util.List;

public class FragmentA extends Fragment implements DashboardAdapter.DashboardClickListener {
    private final static String TAG = FragmentA.class.getSimpleName();
    RecyclerView recyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_a, container, false);
        recyclerview = (RecyclerView) view.findViewById(R.id.recycler_view);
        setRecyclerView();
        return view;
    }

    public void setRecyclerView() {
        recyclerview.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);
        setAdapter();
    }

    public void setAdapter() {
        List<Dashboard> dashboards = new ArrayList<>();

        // 장치를 추가하지 않으셨군ㅇ료?
        dashboards.add(new Dashboard(1));

        // 투약 정보를 추가하지 않으셨군ㅇ료?
        dashboards.add(new Dashboard(2));

        // 개인 정보를 추가하지 않으셨군ㅇ료?
        dashboards.add(new Dashboard(3));

        // 지난 7일간의 내역
        dashboards.add(new Dashboard("지난 7일간의 내역 title",
                "지난 7일간의 내역 content\n" + "18. 11. 23. 00:00 휴머로그 3단위 투여\n"));

        // 혈당 관리 - 좀 세부화해야겠는데?
        dashboards.add(new Dashboard("혈당 관리 title", "혈당 관리 content"));

        // 알림 시간 설정
        dashboards.add(new Dashboard("알림 시간 설정 Title"
                , "Content \n" + "아침 : 07:00 알림\n" + "점심 : 12:00 알림\n" + "..."));

        // 총 투약 횟수
        dashboards.add(new Dashboard("총 투약 횟수 Title"
                , "Content \n" + "11월달 23일 / 30일\n" + "연속 23일째 투약 중"));

        DashboardAdapter mDashboardItems = new DashboardAdapter(dashboards);
        mDashboardItems.setOnClickListener(this);
        recyclerview.setAdapter(mDashboardItems);
    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onAddItemClicked(int position) {

    }

}

