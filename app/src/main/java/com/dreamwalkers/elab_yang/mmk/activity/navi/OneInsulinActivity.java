package com.dreamwalkers.elab_yang.mmk.activity.navi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamwalkers.elab_yang.mmk.R;

public class OneInsulinActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "OneInsulinActivity";
    Context mContext;
    // 설정한 1번 약
    String[] set = {"", "", "", ""};
    CardView card1, card2, card3, card4;
    TextView text1, text2, text3;
    //    Boolean insulin_flag = true;
    CheckBox checkbox1, checkbox2, checkbox3, checkbox4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insulin);
        mContext = this;
        setToolbar();
        setStatusbar();
        set();
    }

    public void setToolbar() {
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setTitle("");
    }

    public void setStatusbar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    public void set() {
        card1 = (CardView) findViewById(R.id.card1);
        card1.setOnClickListener(this);

        card2 = (CardView) findViewById(R.id.card2);
        card2.setOnClickListener(this);

        card3 = (CardView) findViewById(R.id.card3);
        card3.setOnClickListener(this);

        card4 = (CardView) findViewById(R.id.card4);

        // 상위품목
        text1 = (TextView) findViewById(R.id.text1);
//        text1.setOnClickListener(this);
        // 하위품목
        text2 = (TextView) findViewById(R.id.text2);
//        text2.setOnClickListener(this);
        // 단위
        text3 = (TextView) findViewById(R.id.text3);
//        text3.setOnClickListener(this);
        // 투약시간
//        text4 = (TextView) findViewById(R.id.text4);
//        text4.setOnClickListener(this);

        checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
        checkbox1.setOnClickListener(this);
        checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
        checkbox2.setOnClickListener(this);
        checkbox3 = (CheckBox) findViewById(R.id.checkbox3);
        checkbox3.setOnClickListener(this);
        checkbox4 = (CheckBox) findViewById(R.id.checkbox4);
        checkbox4.setOnClickListener(this);

        // 저-장
        Button set_btn = (Button) findViewById(R.id.set_btn);
        set_btn.setOnClickListener(this);
    }

    // 다이얼로그 인터페이스
//    public void showDialog(String title, String context) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        dialog.setTitle(title)
//                .setMessage(context)
//                .setPositiveButton("NO", (dialogInterface, which) -> {
//                });
//        dialog.create();
//        dialog.show();
//    }

    @Override
    public void onClick(View v) {
        // spinner01 : 인슐린 종류(5)
        final String[] items = {getResources().getString(R.string.insulin_name), getResources().getString(R.string.insulin_name1), getResources().getString(R.string.insulin_name2), getResources().getString(R.string.insulin_name3), getResources().getString(R.string.insulin_name4)};
        // spinner02 : 하위 품목
        // 초속효성
        final String[] items1 = {getResources().getString(R.string.insulin_name0_0), getResources().getString(R.string.insulin_name0_1), getResources().getString(R.string.insulin_name0_2)};
        // 속효성
        final String[] items2 = {getResources().getString(R.string.insulin_name1_0)};
        // 중간형
        final String[] items3 = {getResources().getString(R.string.insulin_name2_0), getResources().getString(R.string.insulin_name2_1), getResources().getString(R.string.insulin_name2_2), getResources().getString(R.string.insulin_name2_3)};
        // 혼합형
        final String[] items4 = {getResources().getString(R.string.insulin_name3_0), getResources().getString(R.string.insulin_name3_1), getResources().getString(R.string.insulin_name3_2), getResources().getString(R.string.insulin_name3_3)};
        // 지속형
        final String[] items5 = {getResources().getString(R.string.insulin_name4_0), getResources().getString(R.string.insulin_name4_1)};
        // spinner03 : 식사상태
        final String[] items6 = {getResources().getString(R.string.state_0_0), getResources().getString(R.string.state_0_1), getResources().getString(R.string.state_0_2), getResources().getString(R.string.state_0_3)};
        // 임시사용
        final String[] items99;
        //////////////

        // 스위치문
        switch (v.getId()) {
            case R.id.card1:
                // 품목
                ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, items);
                adapter11.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                // 리스트 목록 클릭 이벤트
                AlertDialog.Builder builder11 = new AlertDialog.Builder(this)
                        .setTitle("품목")
                        .setNegativeButton("취소", null)
                        .setItems(items, (dialog, position) -> {
//                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items[position], Toast.LENGTH_SHORT).show();
                            set[0] = items[position];
                            // 메인 텍스트에 값 넣음
                            text1.setText(items[position]);
                            card1.setBackgroundResource(R.color.lime);
                        });
                builder11.create();
                builder11.show();
                break;

            case R.id.card2:
                // 하위품목
                String mykinds = set[0];
                if (mykinds.equals("초속효성")) {
                    items99 = items1;
                } else if (mykinds.equals("속효성")) {
                    items99 = items2;
                } else if (mykinds.equals("중간형")) {
                    items99 = items3;
                } else if (mykinds.equals("혼합형")) {
                    items99 = items4;
                } else {
                    items99 = items5;
                }
                ArrayAdapter<String> adapter12 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, items99);
                adapter12.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                // 리스트 목록 클릭 이벤트
                AlertDialog.Builder builder12 = new AlertDialog.Builder(this)
                        .setTitle("하위품명")
                        .setNegativeButton("취소", null)
                        .setItems(items99, (dialog, position) -> {
//                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items99[position], Toast.LENGTH_SHORT).show();
                            set[1] = items99[position];
                            text2.setText(items99[position]);
                            card2.setBackgroundResource(R.color.lime);
                        });
                builder12.create();
                builder12.show();
                break;

            case R.id.card3:
                // 단위
                final EditText et = new EditText(this);
                // 숫자만 입력가능하도록 해결
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog.Builder builder13 = new AlertDialog.Builder(this)
                        .setTitle("단위")
                        .setPositiveButton("확인", (dialog, position) -> {
                            set[2] = et.getText().toString();
                            text3.setText(et.getText().toString());
                            card3.setBackgroundResource(R.color.lime);
                        })
                        .setView(et);
                builder13.create();
                builder13.show();
                break;
//            case R.id.checkbox1:
//                break;
//            case R.id.checkbox2:
//                break;
//            case R.id.checkbox3:
//                break;
//            case R.id.checkbox4:
//                break;

            // 투약시간
            // 체크박스로 바꾸자


//                ArrayAdapter<String> adapter14 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, items6);
//                adapter14.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
//                AlertDialog.Builder builder14 = new AlertDialog.Builder(this)
//                        .setTitle("1-4. 식사상태")
//                        .setNegativeButton("NO", null)
//                        .setItems(items6, (dialog, position) -> {
////                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items6[position], Toast.LENGTH_SHORT).show();
//                            set[3] = items6[position];
//                            text4.setText(items6[position]);
//                        });
//                builder14.create();
//                builder14.show();
//                break;

            case R.id.checkbox1:
                if (checkbox1.isChecked() || checkbox2.isChecked() || checkbox3.isChecked() || checkbox4.isChecked()) {
                    card4.setBackgroundResource(R.color.lime);
                } else {
                    card4.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.checkbox2:
                if (checkbox1.isChecked() || checkbox2.isChecked() || checkbox3.isChecked() || checkbox4.isChecked()) {
                    card4.setBackgroundResource(R.color.lime);
                } else {
                    card4.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.checkbox3:
                if (checkbox1.isChecked() || checkbox2.isChecked() || checkbox3.isChecked() || checkbox4.isChecked()) {
                    card4.setBackgroundResource(R.color.lime);
                } else {
                    card4.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.checkbox4:
                if (checkbox1.isChecked() || checkbox2.isChecked() || checkbox3.isChecked() || checkbox4.isChecked()) {
                    card4.setBackgroundResource(R.color.lime);
                } else {
                    card4.setBackgroundResource(R.color.white);
                }
                break;

            // 저장
            case R.id.set_btn:
                String result = "";
                if (checkbox1.isChecked()) result += checkbox1.getText().toString();
                if (checkbox2.isChecked()) result += checkbox2.getText().toString();
                if (checkbox3.isChecked()) result += checkbox3.getText().toString();
                if (checkbox4.isChecked()) result += checkbox4.getText().toString();
                Log.d(TAG, "onClick: result " + result);

                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
//                String set_data = set[0] + "#" + set[1] + "#" + set[2] + "#" + set[3] + "";
                String set_data = set[0] + "#" + set[1] + "#" + set[2] + "#" + result + "";
                editor.putString("SET_DATA", set_data);
                Log.d(TAG, "set_data = " + set_data);

                editor.putString("cache_data_1", "");
                editor.putString("cache_data_2", "");
                editor.putString("cache_data_3", "");
                editor.putString("cache_data_4", "");

                editor.apply();
                finish();
                Toast.makeText(getApplicationContext(), "저장햇슴돠", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}