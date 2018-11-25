package com.dreamwalkers.elab_yang.mmk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.activity.fragment.FragmentA;
import com.dreamwalkers.elab_yang.mmk.activity.fragment.FragmentB;
import com.dreamwalkers.elab_yang.mmk.activity.fragment.FragmentC;
import com.dreamwalkers.elab_yang.mmk.activity.fragment.FragmentD;
import com.dreamwalkers.elab_yang.mmk.activity.navi.EduYoutubeActivity;
import com.dreamwalkers.elab_yang.mmk.activity.select.SelectDrugActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.long1.spacetablayout.SpaceTabLayout;
import io.paperdb.Paper;

public class SpaceTabLayoutActivity extends AppCompatActivity implements IActivityBased, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "SpaceTabLayoutActivity";
    Context mContext;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.spaceTabLayout)
    SpaceTabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initSetting();
    }

    @Override
    public void bindView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initSetting() {
        mContext = this;
        Paper.init(this);
        bindView();
        setStatusbar();
        set();
        setNavi();
    }

    public void setStatusbar() {
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getResources().getColor(R.color.background));
    }

    private void set() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //add the fragments you want to display in a List == 내가 원하면 추가가능하다~ 이말이야
        List<Fragment> fragmentList = new ArrayList<>();
        // 홈 = 기록보기
        fragmentList.add(new FragmentA());
        // 캘린더
        fragmentList.add(new FragmentB());
        // 장치 관리 + 동기화
        fragmentList.add(new FragmentC());
        // 프로필
        fragmentList.add(new FragmentD());

        // set
//        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        tabLayout.initialize(viewPager, getSupportFragmentManager(), fragmentList, null);

        tabLayout.setTabOneOnClickListener(v -> {
//            Snackbar.make(coordinatorLayout, "Welcome to SpaceTabLayout", Snackbar.LENGTH_SHORT).show();
        });
        tabLayout.setOnClickListener(v -> {
            Toast.makeText(this, "" + tabLayout.getCurrentPosition(), Toast.LENGTH_SHORT).show();
//            tabLayout.setTabOneOnClickListener(view -> Snackbar.make(coordinatorLayout, "안녕", Snackbar.LENGTH_SHORT).show());
            tabLayout.setOnClickListener(view -> {
                Toast.makeText(getApplication(), "" + tabLayout.getCurrentPosition(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "set: tabLayout.getCurrentPosition() = " + tabLayout.getCurrentPosition());
                if (tabLayout.getCurrentPosition() == 1) {
                    Log.d(TAG, "set: 여기!!");
                    Intent intent = new Intent(this, SelectDrugActivity.class);
                    startActivity(intent);
                    intent.putExtra("SelectDrugActivity", false);
                }

                if (tabLayout.getCurrentPosition() == 2) {
                    Log.d(TAG, "set: 여기!!");
                    Intent intent = new Intent(this, NeedleScanActivity.class);
                    startActivity(intent);
                    intent.putExtra("flag_toFragment", false);
                }
            });
        });
    }

    // 네비게이션메뉴 설정
    public void setNavi() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    // 뒤로가기 버튼 클릭시
    @Override
    public void onBackPressed() {
        // 네비 접힘
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // 네비게이션메뉴 클릭 이벤트
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_education:
                // 유튜브 영상 페이지 설정
                Log.d(TAG, "onNavigationItemSelected: nav_education");
                startActivity(new Intent(this, EduYoutubeActivity.class));
                break;

            case R.id.nav_set_insulin:
                // 장치 추가
                Log.d(TAG, "onNavigationItemSelected: 띠용");
//                Toast.makeText(getApplicationContext(),"장치 추가", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SelectDrugActivity.class));
                break;
//            case R.id.nav_delete_database:
//                // DATABASE + CACHE CLEAR;
//                Log.d(TAG, "onNavigationItemSelected: nav_delete_database");
//                Toast.makeText(getApplicationContext(), "DATABASE + CACHE CLEAR", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, DeleteDataBaseActivity.class));
//                break;
//
//            case R.id.nav_its_me:
//                // 개발자 정보
//                Log.d(TAG, "onNavigationItemSelected: nav_its_me");
//                Toast.makeText(getApplicationContext(), "개발자 정보", Toast.LENGTH_SHORT).show();
//                break;

//            case R.id.nav_setting:
//                //환경설정
////                Toast.makeText(getApplicationContext(),"환경설정", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, SettingActivity.class));
//                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}

