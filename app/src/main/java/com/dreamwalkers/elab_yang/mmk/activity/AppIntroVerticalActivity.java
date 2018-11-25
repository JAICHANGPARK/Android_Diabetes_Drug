package com.dreamwalkers.elab_yang.mmk.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.dreamwalker.verticalintrov2.VerticalIntro;
import com.dreamwalker.verticalintrov2.VerticalIntroItem;
import com.dreamwalkers.elab_yang.mmk.R;

public class AppIntroVerticalActivity extends VerticalIntro {
    private static final String TAG = "IntroVertical";
    SharedPreferences pref;

    @Override
    protected void onStart() {
        super.onStart();
        // 처음이 아니네?
        check_first();
    }

    @Override
    protected void init() {
        // 상태바 제거
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 1페이지
        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.gray)
//                .image(R.drawable.image_drawer_header)
                .image(R.mipmap.ther)
                .title(getString(R.string.page1_title))
                .titleSize(30)
                .titleColor(R.color.black)
                .text("스마트 니들입니다. KNU 만세")
                .textSize(20)
                .textColor(R.color.black)
                .build());

        // 2페이지
        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.ergo_colorPrimary)
                .image(R.mipmap.anal)
//                .image(R.drawable.image123)
                .title("편리한 혈당기록 가능")
                .titleSize(30)
                .titleColor(R.color.white)
                .text("자동저장해주요")
                .textSize(20)
                .textColor(R.color.white)
                .build());

        // 3페이지
        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.lime)
                .image(R.mipmap.kiosk)
                .title("멈춰! 당뇨야!")
                .titleSize(30)
                .titleColor(R.color.white)
                .text("꾸준한 혈당 관리를 통해 잘 관리합시다")
                .textSize(20)
                .textColor(R.color.white)
                .build());

        setDoneText("시작해볼까요?");
        setTheme(android.R.style.Theme_Holo_Dialog_NoActionBar);
        setSkipEnabled(true);
        setSkipText("스-킵");
        setSkipColor(R.color.black);
        setVibrateEnabled(false);
        setCustomTypeFace(Typeface.createFromAsset(getAssets(), "bmhanna_11yrs_ttf.ttf"));
    }

    // 3페이지 버튼(최종 버튼)
    @Override
    protected Integer setLastItemBottomViewColor() {
        return R.color.colorAccent;
    }

    // 스맵버튼
    @Override
    protected void onSkipPressed(View view) {
        Log.d(TAG, "onSkipPressed: 스키이이입입입입!!");
    }

    // 페이지 넘어갈 때
    @Override
    protected void onFragmentChanged(int position) {
        Log.d(TAG, "onFragmentChanged: 변경 : position" + position);
    }

    // 미지막 버튼 클릭시
    @Override
    protected void onDonePressed() {
        // first_or_second = true;
        check_exec();
        // 전환
        startActivity(new Intent(this, HomeActivity.class));
        Log.d(TAG, "onDonePressed: AppIntroVerticalActivity -> MainActivity");
        finish();
    }

    // 처음 사용자인가?
    private void check_first() {
        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("first_or_second", false)) {
            Log.d(TAG, "check_first: 응 아니야");
//            startActivity(new Intent(this, MainActivity.class));
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    // 실행
    private void check_exec() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("first_or_second", true);
        editor.apply();
    }
}

