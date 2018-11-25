package com.dreamwalkers.elab_yang.mmk.activity.navi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dreamwalkers.elab_yang.mmk.R;

public class ViewInsulinActivity extends AppCompatActivity {
    private final static String TAG = ViewInsulinActivity.class.getSimpleName();
    String[] data_detail = {"", "", "", ""};
    String[] data_detail2 = {"", "", "", ""};
    // data_detail[0] = 품목;
    // data_detail[1] = 하위 품명;
    // data_detail[2] = 단위;
    // data_detail[3] = 투약시간;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_insulin);
        setToolbar();
        setStatusbar();
        //
        TextView text1 = (TextView) findViewById(R.id.text1);
        TextView text2 = (TextView) findViewById(R.id.text2);
        TextView text3 = (TextView) findViewById(R.id.text3);
        TextView text4 = (TextView) findViewById(R.id.text4);
        TextView text5 = (TextView) findViewById(R.id.text5);
        //
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String a1 = "";
        String A1 = pref.getString("SET_DATA", a1);
        text1.setText(A1);

        String a2 = "";
        String A2 = pref.getString("cache_data_1", a2);
        text2.setText(A2);

        String a3 = "";
        String A3 = pref.getString("cache_data_2", a3);
        text3.setText(A3);

        String a4 = "";
        String A4 = pref.getString("cache_data_3", a4);
        text4.setText(A4);

        String a5 = "";
        String A5 = pref.getString("cache_data_4", a5);
        text5.setText(A5);
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
}
