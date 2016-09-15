package com.example.bianney.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.bianney.myapplication.others.MyBeacon;
import com.example.bianney.myapplication.others.MyBeacons;
import com.kontakt.sdk.android.ble.connection.IKontaktDeviceConnection;
import com.kontakt.sdk.android.ble.connection.KontaktDeviceConnection;
import com.kontakt.sdk.android.common.profile.RemoteBluetoothDevice;

import java.util.List;

public class ConnectActivity extends FragmentActivity {

    private static final String TAG = ConnectActivity.class.getSimpleName();
    private static final String LOGBEACON = "Beacon";
    private KontaktDeviceConnection kontaktDeviceConnection;
    private int position = -1;
    private IKontaktDeviceConnection.ConnectionListener connectionListener = new IKontaktDeviceConnection.ConnectionListener() {
        @Override
        public void onConnected() {
            Log.d(TAG, "onConnected");
        }

        @Override
        public void onAuthenticationSuccess(RemoteBluetoothDevice.Characteristics characteristics) {
            MyBeacons.getInstance().getList().get(position).setViewed(true);
            showDialog();
            printCharacteristic(characteristics);
            disconnect();
        }

        @Override
        public void onAuthenticationFailure(int failureCode) {
            switch (failureCode) {
                case KontaktDeviceConnection.FAILURE_WRONG_PASSWORD:
                    Log.d(TAG, "wrong password");
                    break;
                case KontaktDeviceConnection.FAILURE_UNKNOWN_BEACON:
                    Log.d(TAG, "unknow beacon");
                    break;
                default:
                    Log.d(TAG, "not connected");
                    break;
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
                int gattError = KontaktDeviceConnection.getGattError(errorCode);
                Log.d(TAG, "onErrorOccured gattError=" + gattError);
            } else {
                Log.d(TAG, "onErrorOccured=" + errorCode);
            }
            disconnect();
        }

        @Override
        public void onDisconnected() {
            Log.d(TAG, "onDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RemoteBluetoothDevice remoteBluetoothDevice = getIntent().getExtras().getParcelable("beacon");
        String name = remoteBluetoothDevice.getName();
        List<MyBeacon> beaconList = MyBeacons.getInstance().getList();
        position = -1;
        for (int i = 0; i < beaconList.size(); i++){
            if (name.equals(beaconList.get(i).getName())){
                position = i;
            }
        }
        String password = beaconList.get(position).getPassword();
        remoteBluetoothDevice.setPassword(password.getBytes());
        connect(remoteBluetoothDevice);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnect();
    }

    private void connect(RemoteBluetoothDevice remoteBluetoothDevice) {
        kontaktDeviceConnection = new KontaktDeviceConnection(this, remoteBluetoothDevice, connectionListener);
        kontaktDeviceConnection.connect();
    }

    private void disconnect() {
        if (kontaktDeviceConnection != null) {
            kontaktDeviceConnection.close();
            kontaktDeviceConnection = null;
        }
    }

    private void printCharacteristic(RemoteBluetoothDevice.Characteristics characteristics) {
        StringBuilder stringBuilder = new StringBuilder();
        String description = stringBuilder.append("proximity=").append(characteristics.getProximityUUID())
                .append("major=").append(characteristics.getMajor())
                .append("minor=").append(characteristics.getMinor())
                .append("power_level=").append(characteristics.getPowerLevel())
                .append("advertising_interval=").append(characteristics.getAdvertisingInterval())
                .append("active_profile=").append(characteristics.getActiveProfile())
                .append("model_name=").append(characteristics.getModelName())
                .append("namespace=").append(characteristics.getNamespaceId())
                .append("instanceId=").append(characteristics.getInstanceId())
                .append("url=").append(characteristics.getUrl())
                .append("manufacturer_name=").append(characteristics.getManufacturerName())
                .append("battery_level=").append(characteristics.getBatteryLevel())
                .append("firmware_version=").append(characteristics.getFirmwareRevision())
                .append("hardware_version=").append(characteristics.getHardwareRevision())
                .append("secure=").append(characteristics.isSecure())
                .toString();
        Log.d(LOGBEACON, "beacon characteristic= " + description);
    }

    public void showDialog(){
        final SharedPreferences prefs = getSharedPreferences("BeTouristAppUserPreference", MODE_PRIVATE);
        DialogMessage dialog = new DialogMessage();
        dialog.setPosition(position);
        dialog.setPrefs(prefs);
        dialog.show(getSupportFragmentManager(), "");
    }

    public int getPosition (){
        return position;
    }
}