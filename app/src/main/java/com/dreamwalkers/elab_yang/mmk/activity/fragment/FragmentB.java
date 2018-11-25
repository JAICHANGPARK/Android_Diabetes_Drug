package com.dreamwalkers.elab_yang.mmk.activity.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.activity.ProfileActivity_v1;
import com.dreamwalkers.elab_yang.mmk.activity.SelectDrugFirstActivity;
import com.dreamwalkers.elab_yang.mmk.adapter.MyRecyclerAdapter;
import com.dreamwalkers.elab_yang.mmk.adapter.appinfo.ProfileAdapter;
import com.dreamwalkers.elab_yang.mmk.model.CardItem;
import com.dreamwalkers.elab_yang.mmk.model.Profile;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;

public class FragmentB extends Fragment {

//    private CalendarView calendar_view;

    private CollapsibleCalendar calendarView;
    private TextView textview;
    RecyclerView recyclerview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        //        ButterKnife.bind(this, view);
//        calendar_view = (CalendarView) view.findViewById(R.id.calendar_view);
//        calendar_view.getDate();

        calendarView = (CollapsibleCalendar) view.findViewById(R.id.calendarView);
        textview = (TextView) view.findViewById(R.id.textview);

        recyclerview = (RecyclerView) view.findViewById(R.id.recycler_view);
        setRecyclerView();

        calendarView.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() {
                // 날짜 선택 시
                Day day = calendarView.getSelectedDay();
                Log.d(TAG, "onDaySelect: 날짜 선택 = "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay());

                textview.setText((day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay()).toString());
            }

            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {

            }

            @Override
            public void onWeekChange(int i) {

            }
        });

        return view;
    }

    public void setRecyclerView() {
        recyclerview.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);
        setAdapter();
    }

    public void setAdapter() {
        List<CardItem> cardItems = new ArrayList<>();

        // TODO: 2018-11-23 시간은 date로 형식변경하자!!!!!!!!!!!!!!
        cardItems.add(new CardItem(R.mipmap.injection, "15시 30분", "2", "3", "4", "5", R.mipmap.bae));  // 신체정보

        MyRecyclerAdapter mHistoryItems = new MyRecyclerAdapter(cardItems);
        recyclerview.setAdapter(mHistoryItems);
    }
}
