package com.dreamwalkers.elab_yang.mmk.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.dreamwalkers.elab_yang.mmk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity_v1 extends AppCompatActivity implements IActivityBased {
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @BindView(R.id.user_name)
    EditText user_name;

    @BindView(R.id.user_age)
    EditText user_age;

    @BindView(R.id.user_blood_max)
    EditText user_blood_max;

    @BindView(R.id.user_blood_min)
    EditText user_blood_min;

    @BindView(R.id.user_weight)
    EditText user_weight;

    @BindView(R.id.user_height)
    EditText user_height;

    String user_data_name, user_data_age, user_data_blood_min,
            user_data_blood_max, user_data_weight, user_data_height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initSetting();
    }

    @Override
    public void bindView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initSetting() {
        bindView();
        setSupportActionBar(myToolbar);
        getCache();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_drug_select, menu);
        return true;
    }

    // 저장된 cache값을 가져와
    public void getCache() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        user_data_name = pref.getString("user_data0", "");
        user_data_age = pref.getString("user_data1", "");
        user_data_blood_min = pref.getString("user_data2", "");
        user_data_blood_max = pref.getString("user_data3", "");
        user_data_weight = pref.getString("user_data4", "");
        user_data_height = pref.getString("user_data5", "");

        user_name.setText(user_data_name);
        user_age.setText(user_data_age);
        user_blood_min.setText(user_data_blood_min);
        user_blood_max.setText(user_data_blood_max);
        user_weight.setText(user_data_weight);
        user_height.setText(user_data_height);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        set_cache();
    }

    // 후루룹쩝쩝
    public void set_cache() {
        // null값부터 확인하자
        if (user_name.getText().toString().isEmpty())
            user_name.setBackgroundResource(R.color.red);
        if (user_age.getText().toString().isEmpty())
            user_age.setBackgroundResource(R.color.red);
        if (user_blood_min.getText().toString().isEmpty())
            user_blood_min.setBackgroundResource(R.color.red);
        if (user_blood_max.getText().toString().isEmpty())
            user_blood_max.setBackgroundResource(R.color.red);
        if (user_weight.getText().toString().isEmpty())
            user_weight.setBackgroundResource(R.color.red);
        if (user_height.getText().toString().isEmpty())
            user_height.setBackgroundResource(R.color.red);

        // 모두 입력되었다면 저장을 하자
        if (!user_name.getText().toString().isEmpty() && !user_age.getText().toString().isEmpty() && !user_blood_min.getText().toString().isEmpty()
                && !user_blood_max.getText().toString().isEmpty() && !user_weight.getText().toString().isEmpty()
                && !user_height.getText().toString().isEmpty()) {
            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            // 개별 저장
            editor.putString("user_data0", user_name.getText().toString());
            editor.putString("user_data1", user_age.getText().toString());
            editor.putString("user_data2", user_blood_min.getText().toString());
            editor.putString("user_data3", user_blood_max.getText().toString());
            editor.putString("user_data4", user_weight.getText().toString());
            editor.putString("user_data5", user_height.getText().toString());
            editor.apply();
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.select:

                set_cache();
                break;
        }
        return true;
    }
}
