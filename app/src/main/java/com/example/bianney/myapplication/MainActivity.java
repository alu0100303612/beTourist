package com.example.bianney.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bianney.myapplication.others.Monument;
import com.example.bianney.myapplication.others.Monuments;
import com.example.bianney.myapplication.others.MyBeacons;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kontakt.sdk.android.ble.configuration.ActivityCheckConfiguration;
import com.kontakt.sdk.android.ble.configuration.ScanPeriod;
import com.kontakt.sdk.android.ble.configuration.scan.ScanMode;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.device.EddystoneNamespace;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerContract;
import com.kontakt.sdk.android.ble.manager.listeners.EddystoneListener;
import com.kontakt.sdk.android.ble.manager.listeners.ScanStatusListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleEddystoneListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleScanStatusListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IEddystoneDevice;
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int MY_WRITE_EXTERNAL_STORAGE = 0;
    private View mLayout;
    private ProximityManagerContract proximityManager;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyPermission();
        setContentView(R.layout.activity_main);
        MyBeacons.getInstance().getList().get(0).setViewed(false);
        MyBeacons.getInstance().getList().get(1).setViewed(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#0AA492"));
        for(int i = 0; i < toolbar.getChildCount(); i++)
        { View view = toolbar.getChildAt(i);

            if(view instanceof TextView) {
                TextView textView = (TextView) view;

                Typeface myCustomFont= Typeface.createFromAsset(getAssets(), "fonts/OlivesBold.ttf");
                textView.setTypeface(myCustomFont); }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /* MyBeacons */

        KontaktSDK.initialize(String.valueOf(R.string.kontakt_io_api_key));
        proximityManager = new ProximityManager(this);
        configureProximityManager();
        configureListeners();
        configureSpaces();
        /* Fin MyBeacons */

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void configureProximityManager() {
        proximityManager.configuration()
                .scanMode(ScanMode.LOW_LATENCY)
                .scanPeriod(ScanPeriod.create(TimeUnit.SECONDS.toMillis(10), TimeUnit.SECONDS.toMillis(20)))
                .activityCheckConfiguration(ActivityCheckConfiguration.MINIMAL)
                .monitoringEnabled(false);
    }

    private void configureListeners() {
        proximityManager.setEddystoneListener(createEddystoneListener());
        proximityManager.setScanStatusListener(createScanStatusListener());
    }

    private void configureSpaces() {
        List<IEddystoneNamespace> eddystoneNamespaces = new ArrayList<>();
        eddystoneNamespaces.add(EddystoneNamespace.create("Namespace1", "f7826da6bc5b71e0893e", false));
        eddystoneNamespaces.add(EddystoneNamespace.create("Namespace2","f7826da6bc5b71e0893e", false));

        proximityManager.spaces().eddystoneNamespaces(eddystoneNamespaces);
    }

    /* Fin MyBeacons */

    @Override
    protected void onStart() {
        super.onStart();
        client.connect();
        startScanning();
    }

    @Override
    protected void onStop() {
        proximityManager.stopScanning();
        super.onStop();
        client.disconnect();
    }

    @Override
    protected void onDestroy() {
        proximityManager.disconnect();
        proximityManager = null;
        super.onDestroy();
    }

    private void startScanning() {
        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.startScanning();
            }
        });
    }

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private EddystoneListener createEddystoneListener() {
        return new SimpleEddystoneListener() {
            @Override
            public void onEddystoneDiscovered(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
                Log.d("MyBeacon", "Eddystone discovered: " + eddystone.getName());
                for (int i = 0; i < MyBeacons.getInstance().getList().size(); i++){
                    if ((eddystone.getName().equals(MyBeacons.getInstance().getList().get(i).getName()))&& (!MyBeacons.getInstance().getList().get(i).getViewed())){
                            Intent intent = new Intent(MainActivity.this, ConnectActivity.class);
                            intent.putExtra("beacon", eddystone);
                            startActivity(intent);
                    }
                }
            }

            @Override
            public void onEddystonesUpdated(List<IEddystoneDevice> eddystones, IEddystoneNamespace namespace) {
                //Eddystone Update
            }

            @Override
            public void onEddystoneLost(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
                Log.d("MyBeacon", "eariler discovered device has been lost (is considered inactive)");
            }
        };
    }

    private ScanStatusListener createScanStatusListener() {
        return new SimpleScanStatusListener() {
            @Override
            public void onScanStart() {
                showToast("Scanning started");
            }

            @Override
            public void onScanStop() {
                showToast("Scanning stopped");
            }
        };
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            intent = new Intent(this, MapsActivity.class);
        } else if (id == R.id.nav_settings) {
            intent = new Intent(this, ConfigurationActivity.class);
        } else if (id == R.id.nav_about) {
            intent = new Intent(this, AboutActivity.class);
        }
        startActivity(intent);
        return true;
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void verifyPermission() {
        int permission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            Log.d("Permisos", "Permisos aceptados");
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            showPermissionsSnackBar();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_WRITE_EXTERNAL_STORAGE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Permisos", "aceptado");
            } else {
                showPermissionsSnackBar();
            }
        }
    }

    private void showPermissionsSnackBar() {
        Snackbar.make(mLayout, "Permiso de escritura necesario", Snackbar.LENGTH_LONG)
                .setAction("Configuración", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openSettings();
                    }
                }).show();
    }

    public void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    public void onItemClick(int mPosition, int indexPager) {
        try {
            Intent intent = null;
            if (indexPager == 0) {
                intent = new Intent(this, HistoricalActivity.class);
            }
            if (indexPager == 1) {
                intent = new Intent(this, NaturalActivity.class);
            }
            intent.putExtra("mPosition", mPosition);
            startActivity(intent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                String searchText;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final AlertDialog alertDialog = builder.show();
                // Get the layout inflater
                LayoutInflater inflater = this.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_search, null);
                builder.setView(view);

                final EditText text = (EditText) view.findViewById(R.id.search);
                Log.d("TAG", "Palabra: " + text.toString());
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        final String searchText = text.getText().toString();
                        Log.d("TAG", "Palabra: " + searchText);
                        alertDialog.dismiss();
                        searchWord(searchText);


                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alertDialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void searchWord(String word){
        List<Monument> monumentsList = search(word);
        printSearch(monumentsList);
        MainApplication.getInstance().setArraySearch(monumentsList);
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
    public List<Monument> search (String word){
        List<Monument> monumentsSearch = new ArrayList<Monument>();
        for (int i = 0; i < Monuments.getInstance().getHistorical().size(); i++){
            Monument monument = Monuments.getInstance().getHistorical().get(i);
            if ((monument.getName().contains(word))||(monument.getTown().contains(word))){
                monumentsSearch.add(monument);
            }
        }
        for (int i = 0; i < Monuments.getInstance().getNatural().size(); i++){
            Monument monument = Monuments.getInstance().getNatural().get(i);
            if ((monument.getName().toLowerCase().contains(word))||(monument.getTown().toLowerCase().contains(word))){
                monumentsSearch.add(monument);
            }
        }
        return monumentsSearch;
    }

    public void printSearch (List<Monument> monumentsSearch){
        for (int i = 0; i < monumentsSearch.size(); i++){
            Log.d("TAG", "BUSCAR: " + monumentsSearch.get(i).getName() + " MUNICIPIO: " + monumentsSearch.get(i).getTown());
        }
    }
}