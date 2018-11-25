package com.dreamwalkers.elab_yang.mmk.activity.sync.needle.v1

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.dreamwalkers.elab_yang.mmk.R
import com.dreamwalkers.elab_yang.mmk.consts.IntentConst.DEVICEADDRESS
import com.dreamwalkers.elab_yang.mmk.service.knu.deviceneedle.NeedleBLEService
import kotlinx.android.synthetic.main.activity_data_sync.*

class DataSyncActivity : AppCompatActivity() {

    private val REQUEST_PERMISSION_ACCESS_COARSE_LOCATION = 1000

    lateinit var mDeviceAddress: String
    private var mBluetoothLeService: NeedleBLEService? = null
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private lateinit var bluetoothManager: BluetoothManager

    private var mConnected = false

    var count = 0

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, service: IBinder) {
            val binder = service as NeedleBLEService.LocalBinder
            mBluetoothLeService = binder.service
            if (!mBluetoothLeService!!.initialize()) {
                finish()
            }
            mBluetoothLeService!!.connect(mDeviceAddress)
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mBluetoothLeService = null
        }
    }


    private val mGattUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            when (action) {
                NeedleBLEService.ACTION_GATT_CONNECTED -> {
                    mConnected = true
    //                textView.append("연결됨" + "\n")
    //                updateConnectionState(R.string.connected)
                    invalidateOptionsMenu()
                }
                NeedleBLEService.ACTION_GATT_DISCONNECTED -> {
                    mConnected = false
                    animation_view.setAnimation(R.raw.cloud_disconnection)
                    animation_view.playAnimation()
                    msg_text_view.text = "연결 실패.. 다시 시도해주세요"
    //                textView.append("연결 해제"+ "\n")
    //                updateConnectionState(R.string.disconnected)
                    invalidateOptionsMenu()
    //                clearUI()
                }
                NeedleBLEService.ACTION_GATT_SERVICES_DISCOVERED -> {
                    // Show all the supported services and characteristics on the user interface.
    //                displayGattServices(mBluetoothLeService.getSupportedGattServices())
    //                textView.append("서비스 특성 탐색 완료" + "\n")
                }

                NeedleBLEService.ACTION_DATA_AVAILABLE -> {
                    val values = intent.getStringExtra(NeedleBLEService.EXTRA_DATA)
                }

                NeedleBLEService.ACTION_REAL_TIME_START_PHASE -> {
                    msg_text_view.text = "신호 동기화 및 인증 시작 중.."
                }

                NeedleBLEService.ACTION_REAL_TIME_FIRST_PHASE -> {
                    msg_text_view.text = "데이터 수신중..."
                }

                NeedleBLEService.ACTION_REAL_TIME_SECOND_PHASE -> {
                    msg_text_view.text = "수신완료.. 처리중.."
                }

                NeedleBLEService.ACTION_SYNC_DONE -> {
                    val values = intent.getStringExtra(NeedleBLEService.EXTRA_DATA)
    //                toast(values)
                    val intentDone =Intent(this@DataSyncActivity, SyncResultActivity::class.java)
                    intentDone.putExtra("SyncValue", values)
                    startActivity(intentDone)
                    finish()
                }
            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_sync)

        mDeviceAddress = intent.getStringExtra(DEVICEADDRESS)

        checkPermission()
        checkBLESupport()
        val checkBluetoothEnableFlag = checkBluetoothEnable()

        if (checkBluetoothEnableFlag) {
            val gattServiceIntent = Intent(this, NeedleBLEService::class.java)
            bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)
        }

    }

    override fun onResume() {
        super.onResume()

        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter())
        if (mBluetoothLeService != null) {
            val result = mBluetoothLeService!!.connect(mDeviceAddress)
            Log.d("Mains", "Connect request result=$result")
        }

    }

    override fun onPause() {
        super.onPause()
        // TODO: 2018-07-06 브로드케스트 리시버는 꼭 해제해준다. 하지 않게되면 메모리 점유가 진행되어 메모리 예외가 발생한다. - 박제창
        unregisterReceiver(mGattUpdateReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()

        // TODO: 2018-07-06 서비스는 꼭 unbind 해준다. - 박제창
        unbindService(mServiceConnection)
        mBluetoothLeService = null
    }

    private fun makeGattUpdateIntentFilter(): IntentFilter {
        val intentFilter = IntentFilter()
        intentFilter.addAction(NeedleBLEService.ACTION_GATT_CONNECTED)
        intentFilter.addAction(NeedleBLEService.ACTION_GATT_DISCONNECTED)
        intentFilter.addAction(NeedleBLEService.ACTION_GATT_SERVICES_DISCOVERED)
        intentFilter.addAction(NeedleBLEService.ACTION_DATA_AVAILABLE)

        intentFilter.addAction(NeedleBLEService.ACTION_REAL_TIME_START_PHASE)
        intentFilter.addAction(NeedleBLEService.ACTION_REAL_TIME_FIRST_PHASE)
        intentFilter.addAction(NeedleBLEService.ACTION_REAL_TIME_SECOND_PHASE)
        intentFilter.addAction(NeedleBLEService.ACTION_REAL_TIME_FINAL_PHASE)
        intentFilter.addAction(NeedleBLEService.ACTION_SYNC_DONE)

        return intentFilter
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_PERMISSION_ACCESS_COARSE_LOCATION)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_ACCESS_COARSE_LOCATION ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "스캔 권한을 얻었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "권한이 거부됬습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }

    private fun checkBLESupport() {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
//            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun checkBluetoothEnable(): Boolean {
        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter
        // Checks if Bluetooth is supported on the device.
        return if (mBluetoothAdapter == null) {
//            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show()
            finish()
            false
        } else {
            true
        }

    }
}
