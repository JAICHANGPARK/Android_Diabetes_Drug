package com.dreamwalkers.elab_yang.mmk.activity.sync.needle.v1;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.adapter.MyRecyclerAdapter;
import com.dreamwalkers.elab_yang.mmk.database.insulin.DBHelper;
import com.dreamwalkers.elab_yang.mmk.model.CardItem;

import java.util.List;

public class ReceiveDataActivity extends AppCompatActivity {
    private final static String TAG = ReceiveDataActivity.class.getSimpleName();
    private MyRecyclerAdapter mAdapter;
    RecyclerView recycler_view;
    DBHelper db;
    SQLiteDatabase sql;

    String ble_data_append = "";
    String time = "";
    List<CardItem> lists;

    int ble_cnt = 0;
    String[] data_detail = {"", "", "", ""};
//    String[] data_detail2 = {"", "", "", ""};
    // data_detail[0] = 품목;
    // data_detail[1] = 하위 품명;
    // data_detail[2] = 단위;
    // data_detail[3] = 투약시간;

    // 시간 비교용 시간값
    int hour_value = 0;

    // 투약 갯수 플래그
    int needle_cnt_flag = 0;

    // 투약상태
    String state = "";

    // cache1~4 : 투약상태에 따라 4개
    String morning = "";
    String afternoon = "";
    String dinner = "";
    String night = "";

    // ble 시작 인덱스
    int i_start = 0;

    // ble 종료 인덱스
    int i_end = 0;

    String[] ble_data_unit_1;
    int y = 0;

    // 1번과 2번 모두 존재
    String[] one_and_two = {"", ""};
    // 그래서 일번 따로 이번따로 분리한 것
    String[] one = {"", "", "", ""};
    String[] two = {"", "", "", ""};

    SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getdb);
//        setRecyclerView();
        init();
    }

    public void init() {
        needle_one_or_two();
        get_start_index();
        db = new DBHelper(this);
        intent_receive();
        ble_data_cnt();
        check_index();
    }

    // 사용하는 니들이 한개야? 두개야?
    // 플래그를 받아오 ㅏ구별하는거야
    public void needle_one_or_two() {
        needle_cnt_flag = getIntent().getIntExtra("needle_cnt_flag", needle_cnt_flag);
//        flag = 1 : 1개 사용
//        flag = 2 : 2개 사용
        Log.d(TAG, "onCreate: flag " + needle_cnt_flag);
    }

    // 시작 인덱스를 cache에서 가져온다
    public void get_start_index() {
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        i_start = pref.getInt("INDEX", 0);
        Log.d(TAG, "i_start = " + i_start);
        Log.d(TAG, i_start + "번째부터 블루투스값 받으면 돼");
    }

    // ble값 가져와
    public void intent_receive() {
        ble_data_append = getIntent().getStringExtra("BLE");
        Log.d(TAG, "ble_data_append = " + ble_data_append);
    }

    public void ble_data_cnt() {
        // ble 갯수를 세보자
        ble_cnt = getCharNumber(ble_data_append, '&');
        Log.d(TAG, "몇개의 데이터가 있을까? : " + ble_cnt + "개 데이터 존재");
        i_end = getCharNumber(ble_data_append, '&');

        // 어디까지 읽어오는지 그 인덱스를 저-장
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("INDEX", i_end);
        Log.d(TAG, "INDEX = " + i_end + "번째 데이터까지 읽어왔어.. 기억하자");
        editor.apply();
    }

    // 받을 데이터가 있는지 인덱스값 확인
    public void check_index() {
        if (i_start == i_end) {
            // 시작 == 끝?
            // 동기화할 데이터가 없음
            Toast.makeText(getApplicationContext(), "동기화할 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreate: 동기화할 거 없음");
            // 끝내
            finish();
        } else {
            // 틀리면 데이터 받아야지 암!
            connect();
        }
    }

    // 실제 받는 부분
    public void connect() {
        connect_start();
        // 난 인슐린 한개만 쓰는거야
        if (needle_cnt_flag == 1) {
            use_needle_one();

            // 난 2개 써
        } else if (needle_cnt_flag == 2) {
            use_needle_two();
        }
        time = "";
        ble_data_append = "";
        //
        finish();
    }

    public void connect_start() {
        Log.d(TAG, "connect: 받으러 여 메서드에 들어왔다 체크");

        sql = db.getWritableDatabase();

        // ble_data_unit_1 == 블루투스 한 뭉텅이
        ble_data_unit_1 = ble_data_append.split("&");
        // ble_data_unit_1 == 201811062153
        // start -> end 반복하며 저장
        for (y = i_start; y < ble_cnt; y++) {
            Log.d(TAG, "지금 " + (y + ble_cnt) + "번째 진행중");
            Log.d(TAG, "str[y] 전체 = " + ble_data_unit_1[y]);
            // ble_data_unit_1[y] == 20181106
            // 구분
            // TODO: 2018-11-12 date
            Log.d(TAG, "str[y] 년도 = " + ble_data_unit_1[y].substring(0, 4));
            Log.d(TAG, "str[y] 월   = " + ble_data_unit_1[y].substring(4, 6));
            Log.d(TAG, "str[y] 일   = " + ble_data_unit_1[y].substring(6, 8));
            Log.d(TAG, "str[y] 시   = " + ble_data_unit_1[y].substring(8, 10));
            Log.d(TAG, "str[y] 분   = " + ble_data_unit_1[y].substring(10, 12));

            //  0000.00.00 00:00;
            time = ble_data_unit_1[y].substring(0, 4) + "." + ble_data_unit_1[y].substring(4, 6) + "."
                    + ble_data_unit_1[y].substring(6, 8) + " " + ble_data_unit_1[y].substring(8, 10) + ":" + ble_data_unit_1[y].substring(10, 12);
        }
    }

    public void use_needle_one() {

        String set_data = "";
        String only_one_needle_data = "";

        only_one_needle_data = pref.getString("SET_DATA", set_data);
        Log.d(TAG, "onCreate: only_one_needle_data " + only_one_needle_data);

        data_detail = only_one_needle_data.split("#");
//            data_detail[0] = 품목
//            data_detail[1] = 품명
//            data_detail[2] = 단위

        // 지금 시간 = str[y].substring(8, 10)

        // 21-05(8h) : 취침전;
        // 05-11(6h) : 아침식전;
        // 11-16(5h) : 점심식전;
        // 16-21(5h) : 저녁식전;
        hour_value = Integer.parseInt(ble_data_unit_1[y].substring(8, 10));
        Log.d(TAG, "onCreate: hour_value = " + hour_value);

        setDB(time, data_detail[0], data_detail[1], data_detail[2], state);
        time = "";
        ble_data_append = "";

    }

    public void use_needle_two() {
        String a1 = "";
        String a2 = "";
        String a3 = "";
        String a4 = "";

        morning = pref.getString("cache_data_1", a1);  // 아침일경우
        afternoon = pref.getString("cache_data_2", a2);  // 점심
        dinner = pref.getString("cache_data_3", a3);  // 저녁
        night = pref.getString("cache_data_4", a4);  // 취침

        // 아침 값들
        Log.d(TAG, "onCreate: morning " + morning);
        // 점심 값들
        Log.d(TAG, "onCreate: afternoon " + afternoon);
        // 저녁 값들
        Log.d(TAG, "onCreate: dinner " + dinner);
        // 취침 값들
        Log.d(TAG, "onCreate: night " + night);

        // TODO: 2018-11-12 date로..
        // 받아 이걸 시간값을
        hour_value = Integer.parseInt(ble_data_unit_1[y].substring(8, 10));
        Log.d(TAG, "onCreate: hour_value = " + hour_value);

//                one_and_two == 그 사용 시간대에 쓰는게 2개인 경우 스트링 묶은 값
//                one_and_two[0]은 그때 앞에꺼, one_and_two[1]은 그때 뒤에꺼
//                one[0] ~ [2] : 앞에꺼 데이터
//                tow[0] ~ [2] : 뒤에꺼 데이터
        // 아침
        if ((hour_value >= 5) && (hour_value < 11)) {
            setDB_to_hour(morning);
            // 점심
        } else if ((hour_value >= 11) && (hour_value < 16)) {
            setDB_to_hour(afternoon);

            // 저녁
        } else if ((hour_value >= 16) && (hour_value < 21)) {
            setDB_to_hour(dinner);

            // 취침전
        } else {
            setDB_to_hour(night);
        }
    }


    // DB에 저장
    public void setDB(String str1, String str2, String str3, String str4, String str5) {
        sql = db.getWritableDatabase();
        sql.execSQL(String.format("INSERT INTO tb_needle VALUES(null, '%s', '%s', '%s', '%s', '%s')", str1, str2, str3, str4, str5));
        Toast.makeText(getApplicationContext(), "동기화햇구요", Toast.LENGTH_SHORT).show();
        sql.close();
        finish();
    }


    // state에 따라 db저장
    public void setDB_to_hour(String data) {
        if (data.contains("&&")) {
            // && 두개라면 2가지 다 사용하는거고중복인거고
            one_and_two = data.split("&&");
            one = one_and_two[0].split("/");
            two = one_and_two[1].split("/");
            setDB(time, one[0] + "/" + two[0], one[1] + "/" + two[1], one[2] + "/" + two[2], state);

        } else if (data.startsWith("&")) {
            // true 라면 이건 &품목/품명/단위 라는거고
            // 즉 2번 투약
            one_and_two = data.split("&");
            two = one_and_two[1].split("/");
            setDB(time, two[0], two[1], two[2], state);

        } else {
            // 이건 풍목/품명/단위&
            // 즉 1번 투약
            one_and_two = data.split("&");
            one = one_and_two[0].split("/");
            setDB(time, one[0], one[1], one[2], state);
        }
    }

    // 특정문자 반복 갯수 확인
    int getCharNumber(String str, char c) {
        int cnt = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c)
                cnt++;
        }
        return cnt;
    }
}
