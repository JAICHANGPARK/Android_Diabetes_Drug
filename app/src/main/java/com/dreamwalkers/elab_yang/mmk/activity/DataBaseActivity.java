package com.dreamwalkers.elab_yang.mmk.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.adapter.MyRecyclerAdapter;
import com.dreamwalkers.elab_yang.mmk.database.insulin.DBHelper;
import com.dreamwalkers.elab_yang.mmk.model.CardItem;
import com.dreamwalkers.elab_yang.mmk.model.EventCard;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataBaseActivity extends AppCompatActivity implements IActivityBased {
    private final static String TAG = DataBaseActivity.class.getSimpleName();

    // 데이터가 있을 시 : 리사이클러뷰
    @BindView(R.id.data_exist_layout)
    RelativeLayout existLayout;

    // 데이터가 없을 시 : 이미지+텍스트뷰
    @BindView(R.id.data_0_layout)
    RelativeLayout emptyLayout;

    DBHelper db;
    SQLiteDatabase sql;

    List<CardItem> lists;
    private MyRecyclerAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.add_btn)
    Button add_btn;

    EventBus bus = EventBus.getDefault();

    String data;
    //    String abc[] = {"", "", "", "", "", "", ""};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        initSetting();

        setRecyclerView();
        getDB();
    }

    @Override
    public void bindView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initSetting() {
        bindView();
        bus.register(this);

        db = new DBHelper(this);
    }

    public void setRecyclerView() {
        recycler_view.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recycler_view.setLayoutManager(layoutManager);
        try {
            lists = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAdapter = new MyRecyclerAdapter(lists);
        recycler_view.setAdapter(mAdapter);
    }

    public void getDB() {
        sql = db.getReadableDatabase();
        // 화면 clear
        data = "";
        Cursor cursor;
        lists.clear();
        cursor = sql.rawQuery("select*from tb_needle", null);
        while (cursor.moveToNext()) {
            data += cursor.getString(0) + ","
                    + cursor.getString(1) + ","
                    + cursor.getString(2) + ","
                    + cursor.getString(3) + ","
                    + cursor.getString(4) + ","
                    + cursor.getString(5) + "\n";
//            cursor.getString(2) = 인슐린 종류;
            Log.d(TAG, "약값은 " + cursor.getString(2));
            lists.add(new CardItem(setImage(cursor.getString(2)), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), setTimePoint(cursor.getString(5))));
        }

        // 데이터가 이싸뇽?
        if (data.equals("")) {
            Log.d(TAG, "setRecyclerView: 없엉ㅅ");
            setLayout(1);
        } else {
            Log.d(TAG, "getDB: 있어요");
            setLayout(2);
        }
        mAdapter.notifyDataSetChanged();
        cursor.close();
        sql.close();
        Toast.makeText(getApplicationContext(), "조회하였습니다.", Toast.LENGTH_SHORT).show();
    }

    public void setLayout(int a) {
        if (a == 1) {
            // 데이터가 비어있다
            existLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            // 데이터가 존재하네
            existLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }
    }

    // 앞에 이미지를 선택하자
    public int setImage(String str) {
        // 1가지인 경우
        if (str.equals("초속효성")) {
            return R.mipmap.red;
        } else if (str.equals("속효성")) {
            return R.mipmap.bae;
        } else if (str.equals("중간형")) {
            return R.mipmap.green;
        } else if (str.equals("지속성")) {
            return R.mipmap.yellow;
        } else if (str.equals("혼합형")) {
            return R.mipmap.blue;
        }
        // 2가지인 경우
        else if (str.contains("초속효성") && str.contains("속효성")) {
            // 초속
            return R.mipmap.cho_sok;
        } else if (str.contains("초속효성") && str.contains("중간형")) {
            // 초중
            return R.mipmap.cho_joong;
        } else if (str.contains("초속효성") && str.contains("지속성")) {
            // 초지
            return R.mipmap.cho_ji;
        } else if (str.contains("초속효성") && str.contains("혼합형")) {
            // 초혼
            return R.mipmap.cho_hon;
        } else if (str.contains("속효성") && str.contains("중간형")) {
            // 속중
            return R.mipmap.sok_joong;
        } else if (str.contains("속효성") && str.contains("지속성")) {
            // 속지
            return R.mipmap.sok_ji;
        } else if (str.contains("속효성") && str.contains("혼합형")) {
            // 속혼
            return R.mipmap.sok_hon;
        } else if (str.contains("중간형") && str.contains("지속성")) {
            // 중지
            return R.mipmap.joong_ji;
        } else if (str.contains("중간형") && str.contains("혼합형")) {
            // 중혼
            return R.mipmap.joong_hon;
        } else if (str.contains("지속성") && str.contains("혼합형")) {
            // 지혼
            return R.mipmap.ji_hon;
        } else {
            return R.mipmap.happyvirus;
        }
    }

    // 뒤에 이미지 : 타임포인트를 선택하자
    public int setTimePoint(String str) {
        if (str.equals("아침식전")) {
            return R.mipmap.red1;
        } else if (str.equals("점심식전")) {
            return R.mipmap.blue1;
        } else if (str.equals("저녁식전")) {
            return R.mipmap.bae1;
        } else if (str.equals("취침전")) {
            return R.mipmap.yellow1;
        } else {
            return R.mipmap.happyvirus;
        }
    }

    // db에 저장
    public void second_setDB() {
        int cnt = lists.size();
        //        Toast.makeText(getApplicationContext(), "cnt = " + cnt, Toast.LENGTH_SHORT).show();
        sql = db.getWritableDatabase();
        db.onUpgrade(sql, 1, 2);
        for (int i = 0; i < cnt; i++) {
//            Log.d(TAG, i + " = " + lists.get(i).getDate());
//            Log.d(TAG, i + " = " + lists.get(i).getTime());
//            Log.d(TAG, i + " = " + lists.get(i).getSpeed());
//            Log.d(TAG, i + " = " + lists.get(i).getDistance());
//            Log.d(TAG, i + " = " + lists.get(i).getBpm());
//            Log.d(TAG, i + " = " + lists.get(i).getKcal());
            setDB(lists.get(i).getTime(), lists.get(i).getKind(), lists.get(i).getName(), lists.get(i).getUnit(), lists.get(i).getState());
        }
    }

    // DB에 저장하는 메서드
    public void setDB(String str1, String str2, String str3, String str4, String str5) {
        sql = db.getWritableDatabase();
        sql.execSQL(String.format("INSERT INTO tb_needle VALUES(null, '%s', '%s', '%s', '%s', '%s')", str1, str2, str3, str4, str5));
        sql.close();
    }

    @Override
    public void onBackPressed() {
//        Toast.makeText(getApplicationContext(), "뒤로가기버튼 누름", Toast.LENGTH_SHORT).show();
//        showDialog("골라", "저장할까?");
        DataBaseActivity.this.second_setDB();
        DataBaseActivity.this.finish();
    }

    // 뒤로가기 - 다이얼로그
//    public void showDialog(String title, String context) {
//        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
//        dialog.setTitle(title)
//                .setMessage(context)
//                .setPositiveButton("YES", (dialogInterface, which) -> {
//                    // 새로고침 한번 하겠다는 뜻
//                    DataBaseActivity.this.set_setDB();
//                    DataBaseActivity.this.finish();
//                })
//                .setNegativeButton("NO", (dialogInterface, which) -> {
//                    // 그냥 나가겠다는 뜻
//                    finish();
//                });
//        dialog.create();
//        dialog.show();
//    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        bus.unregister(this);
        super.onStop();
    }

    @Subscribe
    public void getEventFromAdapter(EventCard event) {
        Log.e(TAG, "getEventFromAdapter22 : " + event.getPosistion() + event.getTime() + event.getKind() + event.getName() + event.getUnit() + event.getState());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(DataBaseActivity.this).inflate(R.layout.db_refresh_edit_box, null, false);
        builder.setView(view);
        final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
        final EditText edit1 = (EditText) view.findViewById(R.id.edit1);
        final EditText edit2 = (EditText) view.findViewById(R.id.edit2);
        final EditText edit3 = (EditText) view.findViewById(R.id.edit3);
        final EditText edit4 = (EditText) view.findViewById(R.id.edit4);
        final EditText edit5 = (EditText) view.findViewById(R.id.edit5);
        // 기존값 가져오기
        edit1.setText(event.getTime());
        edit2.setText(event.getKind());
        edit3.setText(event.getName());
        edit4.setText(event.getUnit());
        edit5.setText(event.getState());
        ButtonSubmit.setText("삽입");
        final AlertDialog dialog = builder.create();
        ButtonSubmit.setOnClickListener(v1 -> {
            String strEdit1 = edit1.getText().toString();
            String strEdit2 = edit2.getText().toString();
            String strEdit3 = edit3.getText().toString();
            String strEdit4 = edit4.getText().toString();
            String strEdit5 = edit5.getText().toString();
            // 디뽈트값
//            lists.set(event.getPosistion(), new CardItem(setImage(""), null, null, null, null, null, setImage2("")));
            lists.set(event.getPosistion(), new CardItem(setImage(strEdit1), strEdit1, strEdit2, strEdit3, strEdit4, strEdit5, setTimePoint(strEdit5)));
            mAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });
        dialog.show();
    }

    @OnClick(R.id.add_btn)
    void onClick1() {
        // 추가 버튼 클릭시
        showdialog_add();
    }

    private void showdialog_add() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(DataBaseActivity.this)
                .inflate(R.layout.db_add_edit_box, null, false);
        builder.setView(view);

        final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
        final EditText edit1 = (EditText) view.findViewById(R.id.edit1);
        final EditText edit2 = (EditText) view.findViewById(R.id.edit2);
        final EditText edit3 = (EditText) view.findViewById(R.id.edit3);
        final EditText edit4 = (EditText) view.findViewById(R.id.edit4);
        final EditText edit5 = (EditText) view.findViewById(R.id.edit5);

        // 현재시간을 msec 으로 구한다.
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        String formatDate = sdfNow.format(date);

        SimpleDateFormat hhNow = new SimpleDateFormat("HH");
        int hh = Integer.parseInt(hhNow.format(date));
        Log.d(TAG, "showdialog_add: hh = " + hh);

        String add_state;

        if ((hh >= 5) && (hh < 11)) {
            // 아침
            add_state = "아침전";

        } else if ((hh >= 11) && (hh < 16)) {
            // 점심
            add_state = "점심전";

        } else if ((hh >= 16) && (hh < 21)) {
            // 저녁
            add_state = "저녁전";
        } else {
            // 취침전
            add_state = "취침전";
        }

        // default
        edit1.setText(formatDate);
//        edit2.setText();
//        edit3.setText();
        edit4.setText("10");
        edit5.setText(add_state);


        ButtonSubmit.setText("삽입");
        final AlertDialog dialog = builder.create();
        ButtonSubmit.setOnClickListener(v1 -> {
            String strEdit1 = edit1.getText().toString();
            String strEdit2 = edit2.getText().toString();
            String strEdit3 = edit3.getText().toString();
            String strEdit4 = edit4.getText().toString();
            String strEdit5 = edit5.getText().toString();
            // 임시로 default
            lists.add(new CardItem(setImage(strEdit1), strEdit1, strEdit2, strEdit3, strEdit4, strEdit5, setTimePoint(strEdit5)));
            // 내가 추가한게 처음이야? 그럼 뷰를 바꿔야지
            if (lists.size() == 1) {
                setLayout(2);
//                data_exist_layout.setVisibility(View.VISIBLE);
//                data_0_layout.setVisibility(View.GONE);
            }
            mAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });
        dialog.show();
    }
}
