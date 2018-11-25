/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dreamwalkers.elab_yang.mmk.service.knu.deviceneedle;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 */
public class NeedleBLEService extends Service {
    private final static String TAG = NeedleBLEService.class.getSimpleName();

    public final static String ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA = "com.example.bluetooth.le.EXTRA_DATA";

    public final static String ACTION_SERVICE_START_SIGNAL = "com.example.bluetooth.le.ACTION_SERVICE_START_SIGNAL";
    public final static String ACTION_SERVICE_SCAN_DONE = "com.example.bluetooth.le.ACTION_SERVICE_SCAN_DONE";
    public final static String ACTION_FIRST_PHASE_DONE = "com.example.bluetooth.le.ACTION_FIRST_PHASE_DONE";
    public final static String ACTION_SECOND_PHASE_DONE = "com.example.bluetooth.le.ACTION_SECOND_PHASE_DONE";

    public final static String ACTION_REAL_TIME_START_PHASE = "com.example.bluetooth.le.ACTION_REAL_TIME_START_PHASE"; // 인증 시작
    public final static String ACTION_REAL_TIME_FIRST_PHASE = "com.example.bluetooth.le.ACTION_REAL_TIME_FIRST_PHASE"; // 암호화 인증처리
    public final static String ACTION_REAL_TIME_SECOND_PHASE = "com.example.bluetooth.le.ACTION_REAL_TIME_SECOND_PHASE"; // 날짜 인증
    public final static String ACTION_REAL_TIME_FINAL_PHASE = "com.example.bluetooth.le.ACTION_REAL_TIME_FINAL_PHASE"; // a모든 인증 완료 처리

    public final static String ACTION_SYNC_DONE = "com.example.bluetooth.le.ACTION_SYNC_DONE";

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    BluetoothGattCharacteristic mNeedleCharacteristic;

    BluetoothGattCharacteristic mHeartRateMeasurementCharacteristic;
    BluetoothGattCharacteristic mRealtimeWriteCharacteristic;  // 쓰기 위한 특성
    BluetoothGattCharacteristic mTreadmillCharacteristic;
    BluetoothGattCharacteristic mRealtimeCharacteristic;

//    public final static UUID UUID_HEART_RATE_MEASUREMENT = UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);

    boolean firstPhaseCheckerFlag = false;
    boolean secondPhaseCheckerFlag = false;
    boolean dataResultDescriptor = false;
    boolean realTimeDescriptor = false;
    String receivedData = "";
    StringBuilder stringBuilder = new StringBuilder();

    private final BroadcastReceiver mySyncReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            switch (action) {
                case ACTION_SERVICE_START_SIGNAL:
                    broadcastUpdate(ACTION_REAL_TIME_START_PHASE);
                    new Handler().postDelayed(() -> {
                        if (mNeedleCharacteristic != null) {
                            firstPhaseCheckerFlag = startSignal(mNeedleCharacteristic);
                            Log.e(TAG, "onReceive: ACTION_SERVICE_START_SIGNAL --> " + firstPhaseCheckerFlag);
                        }
                    }, 1000);


                    break;
                case ACTION_SERVICE_SCAN_DONE:
                    broadcastUpdate(ACTION_REAL_TIME_FIRST_PHASE);

                    new Handler().postDelayed(() -> {
                        if (mNeedleCharacteristic != null) {
                            secondPhaseCheckerFlag = sendSignal(mNeedleCharacteristic);
                            Log.e(TAG, "onReceive: ACTION_SERVICE_SCAN_DONE --> " + secondPhaseCheckerFlag);
                        }
                    }, 1000);


                    break;

                case ACTION_FIRST_PHASE_DONE:
//                    broadcastUpdate(ACTION_REAL_TIME_SECOND_PHASE);
                    receivedData = stringBuilder.toString();
                    Log.e(TAG, "onReceive: before " + receivedData);
                    receivedData = receivedData.substring(0, receivedData.length() - 1);
                    Log.e(TAG, "onReceive: after" + receivedData);
                    if (receivedData != null) {
                        String[] splitString = receivedData.split("&");
                        Log.e(TAG, "onReceive: splitString size -> " + splitString.length);
                        for (String aSplitString : splitString) {
                            Log.e(TAG, "onReceive: splitString " + aSplitString);
                        }
                        JSONObject obj = null;
                        JSONArray jsonArray = new JSONArray();

                        for (int i = 0; i < splitString.length; i++) {
                            obj = new JSONObject();
                            try {
                                obj.put("id", String.valueOf(i));
                                obj.put("value", splitString[i]);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            jsonArray.put(obj);
                        }
                        JSONObject finalObject = new JSONObject();
                        try {
                            finalObject.put("received", jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e(TAG, "onReceive: finalObject " + finalObject.toString());

                        broadcastUpdate(ACTION_SYNC_DONE, finalObject.toString());
                    }


                    Log.e(TAG, "onReceive: " + "모든 데이터 전달 완료");


                    break;

                case ACTION_SECOND_PHASE_DONE:

                    break;

            }
        }
    };

    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mBluetoothGatt = gatt;
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);
                Log.e(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.e(TAG, "Attempting to start service discovery:" + mBluetoothGatt.discoverServices());

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.e(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction);
            }
        }


        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                for (BluetoothGattService service : gatt.getServices()) {
                    Log.e(TAG, "onServicesDiscovered: " + service.getUuid().toString());

                    if (SampleGattAttributes.BLE_SERVICE_NEEDLE.equals(service.getUuid())) {

                        mNeedleCharacteristic = service.getCharacteristic(SampleGattAttributes.BLE_CHAR_NEEDLE);
                        Log.e(TAG, "onServicesDiscovered:" + mNeedleCharacteristic.getUuid().toString());
                        if (mNeedleCharacteristic != null) {
                            dataResultDescriptor = enableResultNotification(gatt);
                            Log.e(TAG, "onServicesDiscovered: " + "mTreadmillCharacteristic set");
                        }
                    }
                }

//                broadcastUpdate(ACTION_SERVICE_SCAN_DONE);

                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            } else {
                Log.e(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            Log.e(TAG, "onCharacteristicChanged: ");
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
//            super.onDescriptorWrite(gatt, descriptor, status);

            if (status == BluetoothGatt.GATT_SUCCESS) {

                // TODO: 2018-09-05 만약 데이터 결과 디스크립터가 설정되면
                if (dataResultDescriptor) {
                    Log.e(TAG, "onDescriptorWrite: " + "------- dataResultDescriptor Descriptor Write Done");
                    dataResultDescriptor = false;

                    broadcastUpdate(ACTION_SERVICE_START_SIGNAL);
                }

            } else {
                Log.d(TAG, "Callback: Error writing GATT Descriptor: " + status);
            }

        }
    };

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }


    /**
     * 데이터를 처리하는 메소드
     *
     * @param action
     * @param
     */
    private void broadcastUpdate(final String action, String jsonString) {
        final Intent intent = new Intent(action);
        intent.putExtra(EXTRA_DATA, jsonString);
        sendBroadcast(intent);

    }

    /**
     * 데이터를 처리하는 메소드
     *
     * @param action
     * @param characteristic
     */
    private void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);

        if (SampleGattAttributes.BLE_CHAR_NEEDLE.equals(characteristic.getUuid())) {

            final byte[] data = characteristic.getValue();
            if (data != null && data.length > 0) {
                Log.e(TAG, "broadcastUpdate: " + data.length);

                if (firstPhaseCheckerFlag) {
                    if (data[0] == 0x10) {
                        broadcastUpdate(ACTION_SERVICE_SCAN_DONE);
                    }
                }

                if (secondPhaseCheckerFlag) {

                    Log.e(TAG, "broadcastUpdate: " + new String(data));
                    stringBuilder.append(new String(data));
                    if (data[data.length - 1] == 0x03) {
                        broadcastUpdate(ACTION_FIRST_PHASE_DONE);
                    }
                }

            }

            intent.putExtra(EXTRA_DATA, "");
            sendBroadcast(intent);
        }
    }

    public class LocalBinder extends Binder {
        public NeedleBLEService getService() {
            return NeedleBLEService.this;
        }
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SERVICE_START_SIGNAL);
        intentFilter.addAction(ACTION_SERVICE_SCAN_DONE);
        intentFilter.addAction(ACTION_FIRST_PHASE_DONE);
        intentFilter.addAction(ACTION_SECOND_PHASE_DONE);
        return intentFilter;
    }

    @Override
    public IBinder onBind(Intent intent) {
        registerReceiver(mySyncReceiver, makeGattUpdateIntentFilter());
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        unregisterReceiver(mySyncReceiver);
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     * @return Return true if the connection is initiated successfully. The connection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress) && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled        If true, enable notification.  False otherwise.
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

        // This is specific to Heart Rate Measurement.
//        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
//            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
//            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//            mBluetoothGatt.writeDescriptor(descriptor);
//        }
    }

    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;
        return mBluetoothGatt.getServices();
    }

    private boolean startRealTimeSignal(BluetoothGattCharacteristic characteristic) {
        byte[] temp = new byte[4];

        temp[0] = (byte) (0x02);
        temp[1] = (byte) (0x07);
        temp[2] = (byte) (0x70);
        temp[3] = (byte) (0x03);

        characteristic.setValue(temp);
        return mBluetoothGatt.writeCharacteristic(characteristic);

    }


    private boolean startSignal(BluetoothGattCharacteristic characteristic) {


        byte[] sendBytes = new byte[1];
        sendBytes[0] = 0x73;

        for (byte b : sendBytes) {
            Log.e(TAG, "startSignal: " + b);
        }

        characteristic.setValue(sendBytes);

        return mBluetoothGatt.writeCharacteristic(characteristic);

    }

    private boolean sendSignal(BluetoothGattCharacteristic characteristic) {


        byte[] sendBytes = new byte[1];
        sendBytes[0] = 0x61;

        for (byte b : sendBytes) {
            Log.e(TAG, "sendSignal: " + b);
        }

        characteristic.setValue(sendBytes);

        return mBluetoothGatt.writeCharacteristic(characteristic);

    }

    private boolean enableResultNotification(final BluetoothGatt gatt) {
        if (mNeedleCharacteristic == null) {
            return false;
        }
        boolean notiResult = gatt.setCharacteristicNotification(mNeedleCharacteristic, true);
        Log.e(TAG, "enableResultNotification: " + notiResult);
        final BluetoothGattDescriptor d = mNeedleCharacteristic.getDescriptor(SampleGattAttributes.BLE_DESCRIPTOR_DESCRIPTOR);
        d.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        boolean result = gatt.writeDescriptor(d);
        return result;
    }


}
