package com.dreamwalkers.elab_yang.mmk.activity.navi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.activity.IActivityBased;
import com.dreamwalkers.elab_yang.mmk.adapter.YoutubeAdapter;
import com.dreamwalkers.elab_yang.mmk.model.YoutubeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EduYoutubeActivity extends AppCompatActivity implements IActivityBased, YoutubeAdapter.YoutubeViewClickListener {
    //
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    //
    private Handler mHandler;
    // 유튜브 링크 모음
    String youtube_link[] = {
            // 질병관리본부(1)
            "https://www.youtube.com/watch?v=IVaW-aJZ9Vo&t=29s"
            // 삼성서울병원(9)
            , "https://www.youtube.com/watch?v=raciLwP--L0", "https://www.youtube.com/watch?v=s5tbhH8bwEI", "https://www.youtube.com/watch?v=7NDAbacKVqQ"
            , "https://www.youtube.com/watch?v=xIY46uFujZQ", "https://www.youtube.com/watch?v=cnmMqfAliGU", "https://www.youtube.com/watch?v=glte-8TI4L4"
            , "https://www.youtube.com/watch?v=2fKh70kh9tM&t=11s", "https://www.youtube.com/watch?v=19HG-_vNEAU&t=4s", "https://www.youtube.com/watch?v=2A1qBDpbQEs"
            //
            , "", "http://www.samsunghospital.com/home/healthInfo/main.do"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube);
        initSetting();
    }

    @Override
    public void bindView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initSetting() {
        bindView();
        setStatusbar();
        setToolbar();
        recommand_wifi();
        setRecyclerview();
    }

    public void setStatusbar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    public void setToolbar() {
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setTitle("");
    }

    // 1초 후 와이파이 권장 알람
    public void recommand_wifi() {
        mHandler = new Handler();
        runOnUiThread(() -> {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 다이얼로그 이용
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext)
//                            .setTitle("주의")
//                            .setMessage("3G/4G환경에서는 데이터 요금이 발생할 수 있습니다.")
//                            .setPositiveButton("확인", (dialogInterface, which) -> {
//                            });
//                    dialog.create()
//                            .show();

                        // 스낵바 이용
                        Snackbar.make(EduYoutubeActivity.this.getWindow().getDecorView().getRootView(),
                                "3G/4G환경에서는 데이터 요금이 발생할 수 있습니다.", 3000).setAction("확인", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 1000);
        });
    }

    // 리사이클러뷰
    public void setRecyclerview() {
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // 쏐어댑터;
        setAdapter();
    }

    public void setAdapter() {
        List<YoutubeItem> youtubeList = new ArrayList<>();
        // 질병관리본부
        youtubeList.add(new YoutubeItem(R.mipmap.youtube_image_01, "[질병관리본부 - 심뇌혈관질환예방관리]_인포그래픽 동영상_'13년_당뇨병편"));
        // 삼성서울병원
        youtubeList.add(new YoutubeItem(R.mipmap.youtube_image_02, "으랏차차_당뇨_1_당신의 혈당은 안녕하십니까 - 내분비대사내과 김재현 교수[의료진 ON Line 강의]"));
        youtubeList.add(new YoutubeItem(R.mipmap.youtube_image_03, "으랏차차_당뇨_2_당뇨병, 피할 수 있는 방안은 - 내분비대사내과 김재현 교수[의료진 ON Line 강의]"));
        youtubeList.add(new YoutubeItem(R.mipmap.youtube_image_04, "으랏차차_당뇨_3_당뇨병 전단계를 관리하는 법 - 내분비대사내과 김재현 교수[의료진 ON Line 강의]"));
        youtubeList.add(new YoutubeItem(R.mipmap.youtube_image_05, "으랏차차_당뇨_4_당뇨,발견했다면 이것만은 챙기자 - 내분비대사내과 허규연 교수[의료진 ON Line 강의]"));
        youtubeList.add(new YoutubeItem(R.mipmap.youtube_image_06, "으랏차차_당뇨_5_당뇨관리, 밥상과 운동에 답이 있다 - 내분비대사내과 허규연 교수[의료진 ON Line 강의]"));
        youtubeList.add(new YoutubeItem(R.mipmap.youtube_image_07, "으랏차차_당뇨_6_당뇨에 관한 10가지 오해와 진실 - 내분비대사내과 허규연 교수[의료진 ON Line 강의]"));
        youtubeList.add(new YoutubeItem(R.mipmap.youtube_image_08, "자가혈당측정 똑똑하게 활용하기[환자설명자료]"));
        youtubeList.add(new YoutubeItem(R.mipmap.youtube_image_09, "저혈당의 예방과 치료[환자설명자료]"));
        youtubeList.add(new YoutubeItem(R.mipmap.youtube_image_10, "뇌졸중과 당뇨병은 어떤 연관성이 있나요?[1분 메디캠_뇌졸중 편]"));
        // 출처
        youtubeList.add(new YoutubeItem(R.mipmap.img_opentype04, "인포그래픽 동영상_'13년_당뇨병편 저작물은 \n" + "공공누리 제4유형(출처표시+상업적이용금지+변경금지) 조건에 따라 이용할 수 있습니다."));
        youtubeList.add(new YoutubeItem(R.mipmap.samsungseoulhospital, "출처 : 삼성서울병원\n" + "더 자세한 정보를 얻고싶다면 홈페이지를 방문해주세요. 클릭시 이동합니다."));
        //
        YoutubeAdapter mYouItems = new YoutubeAdapter(youtubeList);
        mYouItems.setOnClickListener(this);
        recyclerView.setAdapter(mYouItems);
    }

    // 리사이클러뷰 클릭 이벤트
    @Override
    public void onItemClicked(int position) {
//        Toast.makeText(getApplicationContext(), "선택값 = " + position, Toast.LENGTH_SHORT).show();

        // 출처제외 position 10 아리일 경우 링크 연결
        if (position != 10) {
            onURL(youtube_link[position]);
        }
    }

    // 클릭 시 해당 URL 연결
    public void onURL(String link) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
    }
}
