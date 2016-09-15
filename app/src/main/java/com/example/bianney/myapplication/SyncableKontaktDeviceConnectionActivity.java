package com.example.bianney.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kontakt.sdk.android.ble.connection.IKontaktDeviceConnection;
import com.kontakt.sdk.android.ble.connection.KontaktDeviceConnection;
import com.kontakt.sdk.android.ble.connection.SyncableKontaktDeviceConnection;
import com.kontakt.sdk.android.ble.connection.WriteListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.RemoteBluetoothDevice;

public class SyncableKontaktDeviceConnectionActivity extends AppCompatActivity {

    private static final String TAG = SyncableKontaktDeviceConnectionActivity.class.getSimpleName();

    private SyncableKontaktDeviceConnection syncableKontaktDeviceConnection;

    private IKontaktDeviceConnection.ConnectionListener connectionListener = new IKontaktDeviceConnection.ConnectionListener() {
        @Override
        public void onConnected() {
            Log.d(TAG, "onConnected");
        }

        @Override
        public void onAuthenticationSuccess(RemoteBluetoothDevice.Characteristics characteristics) {
            changeAndSyncMajor();
        }

        @Override
        public void onAuthenticationFailure(int failureCode) {
            if (failureCode == KontaktDeviceConnection.FAILURE_WRONG_PASSWORD) {
                Log.d(TAG, "wrong password");
            } else if (failureCode == KontaktDeviceConnection.FAILURE_UNKNOWN_BEACON){
                Log.d(TAG, "unknown beacon");
            }
            disconnect();
        }

        @Override
        public void onCharacteristicsUpdated(RemoteBluetoothDevice.Characteristics characteristics) {
            Log.d(TAG, "onCharacteristicsUpdated");
        }

        @Override
        public void onErrorOccured(int errorCode) {
            if (KontaktDeviceConnection.isGattError(errorCode)) {
                //low level bluetooth stack error. Most often 133
                int gattError = KontaktDeviceConnection.getGattError(errorCode);
                Log.d(TAG, "onErrorOccured gattError=" + gattError);
            } else {
                //sdk error
                Log.d(TAG, "onErrorOccured=" + errorCode);
            }

            disconnect();
        }

        @Override
        public void onDisconnected() {
            //OnDisconnected function
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init SDK
        KontaktSDK.initialize("your-api-key");
        //we are passing remoteBluetoothDevice and password through intent
        RemoteBluetoothDevice remoteBluetoothDevice = getIntent().getExtras().getParcelable("beacon");
        String password = getIntent().getExtras().getString("password");
        remoteBluetoothDevice.setPassword(password.getBytes());
        connect(remoteBluetoothDevice);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnect();
    }

    private void disconnect() {
        if (syncableKontaktDeviceConnection != null) {
            syncableKontaktDeviceConnection.close();
            syncableKontaktDeviceConnection = null;
        }
    }

    private void connect(RemoteBluetoothDevice remoteBluetoothDevice) {
        syncableKontaktDeviceConnection = new SyncableKontaktDeviceConnection(this, remoteBluetoothDevice, connectionListener);
        syncableKontaktDeviceConnection.connectToDevice();
    }

    private void changeAndSyncMajor() {
        syncableKontaktDeviceConnection.overwriteMajor(200, new SyncableKontaktDeviceConnection.SyncWriteListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "written and synced");
                disconnect();
            }

            @Override
            public void onWriteFailed(WriteListener.Cause cause) {
                Log.d(TAG, "not written to beacon");
                disconnect();
            }
        });
    }
}
