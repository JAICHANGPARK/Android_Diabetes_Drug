package com.dreamwalkers.elab_yang.mmk.activity.sync.needle.v1;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.dreamwalkers.elab_yang.mmk.R;
import com.dreamwalkers.elab_yang.mmk.service.knu.deviceneedle.BluetoothLeService;

import static com.dreamwalkers.elab_yang.mmk.consts.IntentConst.DEVICEADDRESS;
import static com.dreamwalkers.elab_yang.mmk.service.knu.deviceneedle.BluetoothLeService.ACTION_DATA_AVAILABLE;
import static com.dreamwalkers.elab_yang.mmk.service.knu.deviceneedle.BluetoothLeService.ACTION_DATA_AVAILABLE_CHANGE;
import static com.dreamwalkers.elab_yang.mmk.service.knu.deviceneedle.BluetoothLeService.EXTRA_DATA;

public class AutoReceiveActivity extends AppCompatActivity {
    private final static String TAG = AutoReceiveActivity.class.getSimpleName();
    Context mContext;
    TextView text1, text2;

    // 투약 갯수 플래그
    int needle_cnt_flag = 0;

    String deviceAddress = "";

    String receive_ble_data = "";
    String ble_data_append = "";

    Handler mHandler;

    //
    BluetoothLeService mBluetoothLeService = new BluetoothLeService();

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (ACTION_DATA_AVAILABLE_CHANGE.equals(action)) {
                receive_ble_data = (intent.getStringExtra(EXTRA_DATA)).substring(0, 20);
                Log.d(TAG, "receive_ble_data = " + receive_ble_data);
                ble_data_append += receive_ble_data;
            }
            // 블루투스값 쭉 모음 = ble_data_append
            Log.d(TAG, "ble_data_append = " + ble_data_append);
        }
    };

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            mBluetoothLeService.connect(deviceAddress);
            Log.d(TAG, "서비스가 연결되었습니다!");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // 메인
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoreceive);
        mContext = this;
        mHandler = new Handler();
        runOnUiThread(() -> {
            // 1초 후
            mHandler.postDelayed(() -> {
                try {
                    text1.setVisibility(View.GONE);
                    text2.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(AutoReceiveActivity.this, ReceiveDataActivity.class);
                    // 받은 ble 데이터 전체;
                    intent.putExtra("BLE", ble_data_append);
                    // 인슐린 갯수 플래그;
                    intent.putExtra("needle_cnt_flag", needle_cnt_flag);
                    receive_ble_data = "";
                    ble_data_append = "";
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 1000);
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setLottie();
        set();
        //
        deviceAddress = getIntent().getStringExtra(DEVICEADDRESS);
//        flag = getIntent().getIntExtra("flag", flag);

        if (deviceAddress != null) {
            Log.d(TAG, "onCreate: " + deviceAddress);
        }
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction(ACTION_DATA_AVAILABLE);
        intentfilter.addAction(ACTION_DATA_AVAILABLE_CHANGE);
        registerReceiver(mMessageReceiver, intentfilter);
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        // sd카드 데이터 read
        mBluetoothLeService.writeCharacteristic("a");
//        mBluetoothLeService.writeCharacteristic("c");
    }

    public void setLottie() {
        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.loop(true);
        animationView.playAnimation();
    }

    public void set() {
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text1.setVisibility(View.VISIBLE);
        text2.setVisibility(View.GONE);
    }

    // 종료ㅡ 서비스도
    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        handler.postDelayed(r, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        handler.removeCallbacks(r);
    }
}
