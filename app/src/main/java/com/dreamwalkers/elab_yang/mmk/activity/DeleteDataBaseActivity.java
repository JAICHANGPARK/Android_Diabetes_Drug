package com.dreamwalkers.elab_yang.mmk.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.database.insulin.DBHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteDataBaseActivity extends AppCompatActivity implements IActivityBased {
    private static final String TAG = "DeleteDataBaseActivity";
    Context mContext;
    DBHelper db;
    SQLiteDatabase sql;

    @BindView(R.id.delete_button)
    Button delete_button;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletedb);
        initSetting();
    }

    @Override
    public void bindView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initSetting() {
        bindView();
        mContext = this;
        setStatusbar();
        db = new DBHelper(this);
    }

    public void setStatusbar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @OnClick(R.id.delete_button)
    void onClick() {
        Log.d(TAG, "onClick: 삭제 버튼 클릭");
        // 다이얼로그
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("진짜루")
                .setMessage("저장된 내용을 전부 지울까요?")
                .setPositiveButton("네", (dialog, which) -> {
                    // DB삭제
                    Log.d(TAG, "onClick: db 클리어;;;");
                    sql = db.getWritableDatabase();
                    db.onUpgrade(sql, 1, 2);

                    // BLE INDEX cache 삭제
                    pref = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("i_start", 0);
//                        editor.putInt("i_end", 0);
                    editor.apply();
                    finish();
                })
                .setNegativeButton("아니오", (dialog, which) -> Log.d(TAG, "onClick: db 휴 다행"))
                .show()
                .create();
    }
}
