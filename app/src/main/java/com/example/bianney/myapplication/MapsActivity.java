package com.example.bianney.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.bianney.myapplication.others.Historical;
import com.example.bianney.myapplication.others.Monuments;
import com.example.bianney.myapplication.others.Natural;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{
    //http://www.flaticon.com/
    private static final double START_LAT = 28.2936267;
    private static final double START_LONG = -16.5300522;
    private GoogleMap mMap;

    private List<Historical> historical = Monuments.getInstance().getHistorical();
    private List<Natural> natural = Monuments.getInstance().getNatural();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        TextView addressTxt = (TextView) findViewById(R.id.txt_titleMap);
        Typeface face1= Typeface.createFromAsset(getAssets(), "fonts/Street Gathering.ttf");
        addressTxt.setTypeface(face1);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double latitude;
        double longitude;
        // Move the camera to Tenerife
        LatLng tenerife = new LatLng(START_LAT, START_LONG);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tenerife));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(9));

        int historicalSize = Monuments.getInstance().getHistorical().size();
        int naturalSize = Monuments.getInstance().getNatural().size();

        for (int i = 0; i < historicalSize; i++){
            latitude = historical.get(i).getLatitude();
            longitude = historical.get(i).getLongitude();
            int compareLatitude = Double.compare(latitude, -5);
            int compareLongitude = Double.compare(longitude, 8);
            if ((compareLatitude != 0) && (compareLongitude != 0)) {
                LatLng monument = new LatLng(historical.get(i).getLatitude(), historical.get(i).getLongitude());
                MarkerOptions marker = new MarkerOptions().position(monument).title(historical.get(i).getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                mMap.addMarker(marker);
            }
        }

        for (int i = 0; i < naturalSize; i++){
            latitude = natural.get(i).getLatitude();
            longitude = natural.get(i).getLongitude();
            int compareLatitude = Double.compare(latitude, -5);
            int compareLongitude = Double.compare(longitude, 8);
            if ((compareLatitude != 0) && (compareLongitude != 0)) {
                LatLng monument = new LatLng(natural.get(i).getLatitude(), natural.get(i).getLongitude());
                MarkerOptions marker = new MarkerOptions().position(monument).title(natural.get(i).getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                mMap.addMarker(marker);
            }
        }

        final MapsActivity x = this;

        mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                String title = marker.getTitle();
                int type = Monuments.getInstance().getTypeByName(title);
                int position = Monuments.getInstance().getPositionByName(title);
                Intent intent = null;
                if (type == 1) {
                    intent = new Intent(x, HistoricalActivity.class);
                }else if (type == 2){
                    intent = new Intent(x, NaturalActivity.class);
                }
                intent.putExtra("mPosition", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Maps Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.bianney.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Maps Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.bianney.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}