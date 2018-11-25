package com.dreamwalkers.elab_yang.mmk.activity.navi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamwalkers.elab_yang.mmk.R;

public class TwoInsulinActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TwoInsulinActivity";
    Context mContext;
    // 1, 2번 약 배열
    String[] set1 = {"", "", "", ""};
    String[] set2 = {"", "", "", ""};
    //
    CardView card11, card12, card13, card14;
    CardView card21, card22, card23, card24;
    //
    TextView text11, text12, text13;
    TextView text21, text22, text23;

    CheckBox checkbox11, checkbox12, checkbox13, checkbox14;
    CheckBox checkbox21, checkbox22, checkbox23, checkbox24;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twoinsulin);
        mContext = this;
        set();
    }

    public void set() {
        card11 = (CardView) findViewById(R.id.card11);
        card11.setOnClickListener(this);

        card12 = (CardView) findViewById(R.id.card12);
        card12.setOnClickListener(this);

        card13 = (CardView) findViewById(R.id.card13);
        card13.setOnClickListener(this);

        card14 = (CardView) findViewById(R.id.card14);
//        card14.setOnClickListener(this);

        card21 = (CardView) findViewById(R.id.card21);
        card21.setOnClickListener(this);

        card22 = (CardView) findViewById(R.id.card22);
        card22.setOnClickListener(this);

        card23 = (CardView) findViewById(R.id.card23);
        card23.setOnClickListener(this);

        card24 = (CardView) findViewById(R.id.card24);
//        card24.setOnClickListener(this);

        text11 = (TextView) findViewById(R.id.text11);
        text12 = (TextView) findViewById(R.id.text12);
        text13 = (TextView) findViewById(R.id.text13);
//        text14 = (TextView) findViewById(R.id.text14);

        checkbox11 = (CheckBox) findViewById(R.id.checkbox11);
        checkbox12 = (CheckBox) findViewById(R.id.checkbox12);
        checkbox13 = (CheckBox) findViewById(R.id.checkbox13);
        checkbox14 = (CheckBox) findViewById(R.id.checkbox14);

        checkbox21 = (CheckBox) findViewById(R.id.checkbox21);
        checkbox22 = (CheckBox) findViewById(R.id.checkbox22);
        checkbox23 = (CheckBox) findViewById(R.id.checkbox23);
        checkbox24 = (CheckBox) findViewById(R.id.checkbox24);


//        text11.setOnClickListener(this);
//        text12.setOnClickListener(this);
//        text13.setOnClickListener(this);
//        text14.setOnClickListener(this);
        text21 = (TextView) findViewById(R.id.text21);
        text22 = (TextView) findViewById(R.id.text22);
        text23 = (TextView) findViewById(R.id.text23);
//        text24 = (TextView) findViewById(R.id.text24);
//        text21.setOnClickListener(this);
//        text22.setOnClickListener(this);
//        text23.setOnClickListener(this);
//        text24.setOnClickListener(this);
        Button set_btn = (Button) findViewById(R.id.set_btn);
        set_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (checkbox11.isChecked() || checkbox12.isChecked() || checkbox13.isChecked() || checkbox14.isChecked()) {
            card14.setBackgroundResource(R.color.lime);
        } else {
            card14.setBackgroundResource(R.color.white);
        }

        if (checkbox21.isChecked() || checkbox22.isChecked() || checkbox23.isChecked() || checkbox24.isChecked()) {
            card24.setBackgroundResource(R.color.lime);
        } else {
            card24.setBackgroundResource(R.color.white);
        }

        //// 1번 설정에 관해
        // 1번 종류
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
//        final String[] items6 = {getResources().getString(R.string.state_0_0), getResources().getString(R.string.state_0_1), getResources().getString(R.string.state_0_2), getResources().getString(R.string.state_0_3)};
        // 임시사용 1번
        final String[] items99;
        // 임시사용 2번
        final String[] items999;
        // 스위치문
        switch (v.getId()) {
            // 품목
            case R.id.card11:
                ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, items);
                adapter11.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                // 리스트 목록 클릭 이벤트
                AlertDialog.Builder builder11 = new AlertDialog.Builder(this)
                        .setTitle("1-1. 종류")
                        .setNegativeButton("NO", null)
                        .setItems(items, (dialog, position) -> {
//                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items[position], Toast.LENGTH_SHORT).show();
                            set1[0] = items[position];
                            // 메인 텍스트에 값 넣음
                            text11.setText(items[position]);
                            card11.setBackgroundResource(R.color.lime);
                        });
                builder11.create();
                builder11.show();
                break;

            case R.id.card12:
                // 하위품목
                String mykinds = set1[0];
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
                        .setTitle("1-2. 하위품명")
                        .setNegativeButton("NO", null)
                        .setItems(items99, (dialog, position) -> {
//                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items99[position], Toast.LENGTH_SHORT).show();
                            set1[1] = items99[position];
                            text12.setText(items99[position]);
                            card12.setBackgroundResource(R.color.lime);
                        });
                builder12.create();
                builder12.show();
                break;

            case R.id.card13:
                // 단위
                final EditText et = new EditText(this);
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog.Builder builder13 = new AlertDialog.Builder(this)
                        .setTitle("1-3. 단위")
                        .setPositiveButton("저장", (dialog, position) -> {
                            set1[2] = et.getText().toString();
                            text13.setText(et.getText().toString());
                            card13.setBackgroundResource(R.color.lime);
                        })
                        .setView(et);
                builder13.create();
                builder13.show();
                break;

//            case R.id.text14:
            // 투약시간
//                ArrayAdapter<String> adapter14 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, items6);
//                adapter14.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
//                AlertDialog.Builder builder14 = new AlertDialog.Builder(this)
//                        .setTitle("1-4. 식사상태")
//                        .setNegativeButton("NO", null)
//                        .setItems(items6, (dialog, position) -> {
////                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items6[position], Toast.LENGTH_SHORT).show();
//                            set1[3] = items6[position];
//                            text14.setText(items6[position]);
//                        });
//                builder14.create();
//                builder14.show();
//                break;


            // 이제 2번째 약 설정
            case R.id.card21:
                // 품목
                ArrayAdapter<String> adapter21 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, items);
                adapter21.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                // 리스트 목록 클릭 이벤트
                AlertDialog.Builder builder21 = new AlertDialog.Builder(this)
                        .setTitle("2-1. 종류")
                        .setNegativeButton("NO", null)
                        .setItems(items, (dialog, position) -> {
//                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items[position], Toast.LENGTH_SHORT).show();
                            set2[0] = items[position];
                            // 메인 텍스트에 값 넣음
                            text21.setText(items[position]);
                            card21.setBackgroundResource(R.color.lime);
                        });
                builder21.create();
                builder21.show();
                break;

            case R.id.card22:
                // 하위품목
                String mykinds2 = set2[0];
                if (mykinds2.equals("초속효성")) {
                    items999 = items1;
                } else if (mykinds2.equals("속효성")) {
                    items999 = items2;
                } else if (mykinds2.equals("중간형")) {
                    items999 = items3;
                } else if (mykinds2.equals("혼합형")) {
                    items999 = items4;
                } else {
                    items999 = items5;
                }
                ArrayAdapter<String> adapter22 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, items999);
                adapter22.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                // 리스트 목록 클릭 이벤트
                AlertDialog.Builder builder22 = new AlertDialog.Builder(this)
                        .setTitle("2-2. 하위품명")
                        .setNegativeButton("NO", null)
                        .setItems(items999, (dialog, position) -> {
//                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items99[position], Toast.LENGTH_SHORT).show();
                            set2[1] = items999[position];
                            text22.setText(items999[position]);
                            card22.setBackgroundResource(R.color.lime);
                        });
                builder22.create();
                builder22.show();
                break;

            case R.id.card23:
                // 단위
                final EditText et2 = new EditText(this);
                et2.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog.Builder builder23 = new AlertDialog.Builder(this)
                        .setTitle("2-3. 단위")
                        .setPositiveButton("저장", (dialog, position) -> {
                            set2[2] = et2.getText().toString();
                            text23.setText(et2.getText().toString());
                            card23.setBackgroundResource(R.color.lime);
                        })
                        .setView(et2);
                builder23.create();
                builder23.show();
                break;

//            case R.id.text24:
            // 투약시간
//                ArrayAdapter<String> adapter24 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, items6);
//                adapter24.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
//                AlertDialog.Builder builder24 = new AlertDialog.Builder(this)
//                        .setTitle("2-4. 식사상태")
//                        .setNegativeButton("NO", null)
//                        .setItems(items6, (dialog, position) -> {
////                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items6[position], Toast.LENGTH_SHORT).show();
//                            set2[3] = items6[position];
//                            text24.setText(items6[position]);
//                        });
//                builder24.create();
//                builder24.show();
//                break;

            // 저장
            case R.id.set_btn:
                String result = "";
                if (checkbox11.isChecked()) result += checkbox11.getText().toString();
                if (checkbox12.isChecked()) result += checkbox12.getText().toString();
                if (checkbox13.isChecked()) result += checkbox13.getText().toString();
                if (checkbox14.isChecked()) result += checkbox14.getText().toString();
//                Log.d(TAG, "onClick: result " + result);

                String insulin_data1 = set1[0] + "/" + set1[1] + "/" + set1[2];
                Log.d(TAG, "onClick: insulin_data1 = " + insulin_data1);
                Log.d(TAG, "onClick: result " + result);


                String result2 = "";
                if (checkbox21.isChecked()) result2 += checkbox21.getText().toString();
                if (checkbox22.isChecked()) result2 += checkbox22.getText().toString();
                if (checkbox23.isChecked()) result2 += checkbox23.getText().toString();
                if (checkbox24.isChecked()) result2 += checkbox24.getText().toString();

                String insulin_data2 = set2[0] + "/" + set2[1] + "/" + set2[2];
                Log.d(TAG, "onClick: insulin_data2 = " + insulin_data2);
                Log.d(TAG, "onClick: result2 " + result2);

                String cache_data_1 = "";  // 아침전
                String cache_data_2 = "";  // 점심전
                String cache_data_3 = "";  // 저녁전
                String cache_data_4 = "";  // 취침전

                if (checkbox11.isChecked()) cache_data_1 += insulin_data1 + "&";
                if (checkbox21.isChecked()) cache_data_1 += "&" + insulin_data2;

                if (checkbox12.isChecked()) cache_data_2 += insulin_data1 + "&";
                if (checkbox22.isChecked()) cache_data_2 += "&" + insulin_data2;

                if (checkbox13.isChecked()) cache_data_3 += insulin_data1 + "&";
                if (checkbox23.isChecked()) cache_data_3 += "&" + insulin_data2;

                if (checkbox14.isChecked()) cache_data_4 += insulin_data1 + "&";
                if (checkbox24.isChecked()) cache_data_4 += "&" + insulin_data2;

                Log.d(TAG, "onClick: cache_data_1" + cache_data_1);
                Log.d(TAG, "onClick: cache_data_2" + cache_data_2);
                Log.d(TAG, "onClick: cache_data_3" + cache_data_3);
                Log.d(TAG, "onClick: cache_data_4" + cache_data_4);

                // 캐시에 저장
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
//                String set_data = set1[0] + "#" + set1[1] + "#" + set1[2] + "#" + result + "%" + set2[0] + "#" + set2[1] + "#" + set2[2] + "#" + result2;
                editor.putString("cache_data_1", cache_data_1);
                editor.putString("cache_data_2", cache_data_2);
                editor.putString("cache_data_3", cache_data_3);
                editor.putString("cache_data_4", cache_data_4);

                editor.putString("SET_DATA", "");

//                editor.putString("SET_DATA", set_data);
//                Log.d(TAG, "set_data = " + set_data);
                editor.apply();
                finish();
                Toast.makeText(getApplicationContext(), "2개 저장햇슴돠", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
