package com.dreamwalkers.elab_yang.mmk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.activity.select.SelectDrugActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.annotation.NonNull;
//import android.support.design.widget.NavigationView;
//import android.support.design.widget.Snackbar;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.dreamwalkers.elab_yang.mmk.R;
//import com.dreamwalkers.elab_yang.mmk.activity.navi.AlyakActivity;
//import com.dreamwalkers.elab_yang.mmk.activity.navi.EditProfileActivity;
//import com.dreamwalkers.elab_yang.mmk.activity.navi.EduYoutubeActivity;
//import com.dreamwalkers.elab_yang.mmk.activity.navi.OneInsulinActivity;
//import com.dreamwalkers.elab_yang.mmk.activity.navi.ProfileActivity;
//import com.dreamwalkers.elab_yang.mmk.activity.navi.TwoInsulinActivity;
//import com.dreamwalkers.elab_yang.mmk.activity.navi.ViewInsulinActivity;
//import com.dreamwalkers.elab_yang.mmk.activity.select.SelectDrugActivity;
//import com.dreamwalkers.elab_yang.mmk.adapter.DeviceAdapter;
//import com.dreamwalkers.elab_yang.mmk.model.Device;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import io.paperdb.Paper;
//
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IActivityBased {
    private static final String TAG = "MainActivity";

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    //
//    @BindView(R.id.recycler_view)
////    RecyclerView recyclerView;
////
////    SharedPreferences pref;
    Context mContext;

    ////
////    DeviceAdapter deviceAdapter;
////    HashSet<Device> deviceDatabase = new HashSet<>();
////    ArrayList<Device> deviceArrayList;
////    //    ImageView big_size_image;
////    private Handler mHandler;
////    //     처음 클리어 플래그
//////    Boolean first_clear_flag = false;
////    AlertDialog alert;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
        mContext = this;
        initSetting();
//////        hoxy_first();
    }
    //
//
    @Override
    public void bindView() {
        ButterKnife.bind(this);
    }

    //
    @Override
    public void initSetting() {
        Paper.init(this);
        bindView();
//        setStatusbar();
        set();
        setNavi();
//        setDevice();
    }

    ////
////    // 상태바
////    public void setStatusbar() {
//////        Window window = getWindow();
//////        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//////        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//////        window.setStatusBarColor(getResources().getColor(R.color.ella_background));
////
////        // 앱 아이콘 검정색으로 변경
////        View view = getWindow().getDecorView();
////        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
////        getWindow().setStatusBarColor(getResources().getColor(R.color.background));
////    }
////
    public void set() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

//        Button test1 = (Button) findViewById(R.id.test1);
//        test1.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SpaceTabLayoutActivity.class)));

//        Button test2 = (Button) findViewById(R.id.test2);
//        test2.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TabsHeaderActivity.class)));
    }

    ////
    // 네비게이션메뉴 설정
    public void setNavi() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    ////
//    // deviceAdapter 불러오기
//    public void setDevice() {
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
//        deviceDatabase = Paper.book("device").read("user_device");
//        if (deviceDatabase != null) {
//            if (deviceDatabase.size() != 0) {
//                deviceArrayList = new ArrayList<>(deviceDatabase);
//                deviceAdapter = new DeviceAdapter(this, deviceArrayList);
//                recyclerView.setAdapter(deviceAdapter);
//                for (int i = 0; i < deviceArrayList.size(); i++) {
//                    Device device = deviceArrayList.get(i);
//                }
//            }
//        } else {
//            Log.d(TAG, "setDevice: 장치x");
//        }
//    }
////
////    // 뒤로가기 버튼 클릭시
////    @Override
////    public void onBackPressed() {
////        // 네비 접힘
////        if (drawer.isDrawerOpen(GravityCompat.START)) {
////            drawer.closeDrawer(GravityCompat.START);
////        } else {
////            super.onBackPressed();
////        }
////    }
////
    // 네비게이션메뉴 클릭 이벤트
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        pref = getSharedPreferences("pref", MODE_PRIVATE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = menuItem.getItemId();
        switch (id) {
//            case R.id.nav_profile:
//                // 호구조사
//                Log.d(TAG, "onNavigationItemSelected: nav_profile");
////                Toast.makeText(getApplicationContext(),"장치 추가", Toast.LENGTH_SHORT).show();
//                String cache_user_data = pref.getString("user_data0", "");
//                if (cache_user_data.equals("")) {
//                    // 데이터가 없네 저장부터하셈ㅡㅡ
//                    Snackbar.make(getWindow().getDecorView().getRootView(), "넌 이름이 뭐니.", 3000).setAction("확인", v -> {
//                        startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
//                    }).show();
//                } else {
//                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
//                }
//                break;

            case R.id.nav_set_insulin:
                // 장치 추가
//                Toast.makeText(getApplicationContext(),"장치 추가", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, SelectDrugActivity.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
//
//    // 상태바
//    public void setStatusbar() {
////        Window window = getWindow();
////        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
////        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////        window.setStatusBarColor(getResources().getColor(R.color.ella_background));
//
//        // 앱 아이콘 검정색으로 변경
//        View view = getWindow().getDecorView();
//        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        getWindow().setStatusBarColor(getResources().getColor(R.color.background));
//    }
//
//    public void set() {
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//
//        Button test1 = (Button) findViewById(R.id.test1);
//        test1.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SpaceTabLayoutActivity.class)));
//
//        Button test2 = (Button) findViewById(R.id.test2);
////        test2.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TabsHeaderActivity.class)));
//    }
//
//    // 네비게이션메뉴 설정
//    public void setNavi() {
//        setSupportActionBar(myToolbar);
//        getSupportActionBar().setTitle("");
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        navigationView.setNavigationItemSelectedListener(this);
//    }
//
//    // deviceAdapter 불러오기
//    public void setDevice() {
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
//        deviceDatabase = Paper.book("device").read("user_device");
//        if (deviceDatabase != null) {
//            if (deviceDatabase.size() != 0) {
//                deviceArrayList = new ArrayList<>(deviceDatabase);
//                deviceAdapter = new DeviceAdapter(this, deviceArrayList);
//                recyclerView.setAdapter(deviceAdapter);
//                for (int i = 0; i < deviceArrayList.size(); i++) {
//                    Device device = deviceArrayList.get(i);
//                }
//            }
//        } else {
//            Log.d(TAG, "setDevice: 장치x");
//        }
//    }
//
//    // 뒤로가기 버튼 클릭시
//    @Override
//    public void onBackPressed() {
//        // 네비 접힘
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    // 네비게이션메뉴 클릭 이벤트
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        pref = getSharedPreferences("pref", MODE_PRIVATE);
//        int id = menuItem.getItemId();
//        switch (id) {
//            case R.id.nav_profile:
//                // 호구조사
//                Log.d(TAG, "onNavigationItemSelected: nav_profile");
////                Toast.makeText(getApplicationContext(),"장치 추가", Toast.LENGTH_SHORT).show();
//                String cache_user_data = pref.getString("user_data0", "");
//                if (cache_user_data.equals("")) {
//                    // 데이터가 없네 저장부터하셈ㅡㅡ
//                    Snackbar.make(getWindow().getDecorView().getRootView(), "넌 이름이 뭐니.", 3000).setAction("확인", v -> {
//                        startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
//                    }).show();
//                } else {
//                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
//                }
//                break;
//
//            case R.id.nav_view_insulin:
//                // 인슐린 보기
//                Log.d(TAG, "onNavigationItemSelected: nav_view_insulin");
////                Toast.makeText(getApplicationContext(),"인슐린 보기", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, ViewInsulinActivity.class));
//                break;
//
//            case R.id.nav_set_insulin:
//                // 인슐린 설정
//                Log.d(TAG, "onNavigationItemSelected: nav_set_insulin");
////                onCreateDialog();
//
//                startActivity(new Intent(this, SelectDrugActivity.class));
//                Toast.makeText(getApplicationContext(), "인슐린 설정", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.nav_add_device:
//                // 장치 추가
//                Log.d(TAG, "onNavigationItemSelected: nav_add_device");
//                Toast.makeText(getApplicationContext(), "장치 추가", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, NeedleScanActivity.class));
//                break;
//
//            case R.id.nav_view_database:
//                // VIEW DATABASE
//                Log.d(TAG, "onNavigationItemSelected: nav_view_database");
//                Toast.makeText(getApplicationContext(), "VIEW DATABASE", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, DataBaseActivity.class));
//                break;
//
//            case R.id.nav_education:
//                // 유튜브 영상 페이지 설정
//                Log.d(TAG, "onNavigationItemSelected: nav_education");
//                startActivity(new Intent(MainActivity.this, EduYoutubeActivity.class));
//                break;
//
//            case R.id.nav_edit_profile:
//                // 개인정보 입력
//                Log.d(TAG, "onNavigationItemSelected: nav_edit_profile");
//                startActivity(new Intent(MainActivity.this, SettingActivity.class));
//                break;
//
////            case R.id.nav_delete_database:
////                // DATABASE + CACHE CLEAR;
////                Log.d(TAG, "onNavigationItemSelected: nav_delete_database");
////                Toast.makeText(getApplicationContext(), "DATABASE + CACHE CLEAR", Toast.LENGTH_SHORT).show();
////                startActivity(new Intent(MainActivity.this, DeleteDataBaseActivity.class));
//
////                break;
////
////            case R.id.nav_view_insulin:
////                // 인슐린 보기
////                Log.d(TAG, "onNavigationItemSelected: nav_view_insulin");
//////                Toast.makeText(getApplicationContext(),"인슐린 보기", Toast.LENGTH_SHORT).show();
////                startActivity(new Intent(MainActivity.this, ViewInsulinActivity.class));
////                break;
////
////            case R.id.nav_set_insulin:
////                // 인슐린 설정
////                Log.d(TAG, "onNavigationItemSelected: nav_set_insulin");
////                onCreateDialog();
////                Toast.makeText(getApplicationContext(), "인슐린 설정", Toast.LENGTH_SHORT).show();
////                break;
////
////            case R.id.nav_add_device:
////                // 장치 추가
////                Log.d(TAG, "onNavigationItemSelected: nav_add_device");
////                Toast.makeText(getApplicationContext(), "장치 추가", Toast.LENGTH_SHORT).show();
////                startActivity(new Intent(MainActivity.this, NeedleScanActivity.class));
////                break;
////
////            case R.id.nav_view_database:
////                // VIEW DATABASE
////                Log.d(TAG, "onNavigationItemSelected: nav_view_database");
////                Toast.makeText(getApplicationContext(), "VIEW DATABASE", Toast.LENGTH_SHORT).show();
////                startActivity(new Intent(MainActivity.this, DataBaseActivity.class));
////                break;
////
////            case R.id.nav_education:
////                // 유튜브 영상 페이지 설정
////                Log.d(TAG, "onNavigationItemSelected: nav_education");
////                startActivity(new Intent(MainActivity.this, EduYoutubeActivity.class));
////                break;
////
////            case R.id.nav_edit_profile:
////                // 개인정보 입력
////                Log.d(TAG, "onNavigationItemSelected: nav_edit_profile");
////                startActivity(new Intent(MainActivity.this, SettingActivity.class));
////                break;
////
//////            case R.id.nav_delete_database:
//////                // DATABASE + CACHE CLEAR;
//////                Log.d(TAG, "onNavigationItemSelected: nav_delete_database");
//////                Toast.makeText(getApplicationContext(), "DATABASE + CACHE CLEAR", Toast.LENGTH_SHORT).show();
//////                startActivity(new Intent(MainActivity.this, DeleteDataBaseActivity.class));
//////                break;
//////
//////            case R.id.nav_its_me:
//////                // 개발자 정보
//////                Log.d(TAG, "onNavigationItemSelected: nav_its_me");
//////                Toast.makeText(getApplicationContext(), "개발자 정보", Toast.LENGTH_SHORT).show();
//////                break;
////
//////            case R.id.nav_setting:
//////                //환경설정
////////                Toast.makeText(getApplicationContext(),"환경설정", Toast.LENGTH_SHORT).show();
//////                startActivity(new Intent(MainActivity.this, SettingActivity.class));
//////                break;
////        }
////        drawer.closeDrawer(GravityCompat.START);
////        return false;
////    }
////
////    private void onCreateDialog() {
////        final String[] items = {"인슐린 1개", "인슐린 2개", "알ㅡ약"};
////        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
////        builder.setTitle("선택해")
////                .setSingleChoiceItems(items, 0, (DialogInterface dialog, int which) -> {
////                    Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
////                    if (which == 0) {
////                        // 약 1개
////                        startActivity(new Intent(MainActivity.this, OneInsulinActivity.class));
////                    } else if (which == 1) {
////                        // 약 2개
////                        startActivity(new Intent(MainActivity.this, TwoInsulinActivity.class));
////                    } else {
////                        // 알약
////                        startActivity(new Intent(MainActivity.this, AlyakActivity.class));
////                    }
////                    dialog.dismiss(); // 누르면 바로 닫히는 형태
////                })
////                .show();
////    }
//}
////}