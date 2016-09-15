package com.example.bianney.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bianney.myapplication.others.Historical;
import com.example.bianney.myapplication.others.Monuments;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class HistoricalActivity extends FragmentActivity implements OnMapReadyCallback {
    //http://www.flaticon.com/
    Historical historical = null;
    private static final double START_LAT = 28.2936267;
    private static final double START_LONG = -16.5300522;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);
        TextView nameTxt = (TextView) findViewById(R.id.txt_name);
        TextView townTxt = (TextView) findViewById(R.id.txt_town_two);
        TextView addressTxt = (TextView) findViewById(R.id.txt_address_two);
        TextView descriptionTxt = (TextView) findViewById(R.id.txt_description_two);
        ImageView monumentImg = (ImageView) findViewById(R.id.img_monument);
        ImageView monumentImgDescrp = (ImageView) findViewById(R.id.img_description);
        ImageView monumentImgTown = (ImageView) findViewById(R.id.img_town);
        ImageView monumentImgAddre = (ImageView) findViewById(R.id.img_address);
        ImageView monumentImgInfr = (ImageView) findViewById(R.id.img_phoneNumber);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try{
            Bundle extras = getIntent().getExtras();
            int position = extras.getInt("mPosition");
            historical = (Historical) Monuments.getInstance().getHistorical().get(position);
            Log.d("Error", historical.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try{
            int imageResource = getResources().getIdentifier(historical.getImage()+"big", "drawable", getPackageName());
            Drawable originalDrawable= this.getResources().getDrawable(imageResource);
            Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
            //creamos el drawable redondeado
            RoundedBitmapDrawable roundedDrawable =
                    RoundedBitmapDrawableFactory.create(this.getResources(), originalBitmap);
            //asignamos el CornerRadius
            roundedDrawable.setCornerRadius(originalBitmap.getHeight());
            monumentImg.setImageDrawable(roundedDrawable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            monumentImgDescrp.setImageResource(R.drawable.ic_address_verde_azul);
            monumentImgTown.setImageResource(R.drawable.ic_earth_globe_verde_azul);
            monumentImgAddre.setImageResource(R.drawable.ic_marker_verde_azul);
            monumentImgInfr.setImageResource(R.drawable.ic_information_verde_azul);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        try{

            Typeface face1= Typeface.createFromAsset(getAssets(), "fonts/Street Gathering.ttf");
            nameTxt.setTypeface(face1);
            nameTxt.setText(historical.getName());
            townTxt.setText(historical.getTown());
            addressTxt.setText(historical.getAddress());
            if ("español".equals(Locale.getDefault().getDisplayLanguage())) {
                descriptionTxt.setText(historical.getSpanishDescription());
            } else if ("English".equals(Locale.getDefault().getDisplayLanguage())){
                descriptionTxt.setText(historical.getEnglishDescription());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        // Add a marker in Sydney and move the camera
        LatLng tenerife = new LatLng(START_LAT, START_LONG);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tenerife));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(9));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng monument = new LatLng (historical.getLatitude(), historical.getLongitude());
        Drawable circle = getResources().getDrawable(R.drawable.ic_monument);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(circle.getIntrinsicWidth(), circle.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        circle.setBounds(0, 0, circle.getIntrinsicWidth(), circle.getIntrinsicHeight());
        circle.draw(canvas);
        BitmapDescriptor bd = BitmapDescriptorFactory.fromBitmap(bitmap);
        mMap.addMarker(new MarkerOptions()
                .position(monument)
                .title(historical.getName())
                .snippet(historical.getAddress())
                .icon(bd)
                .anchor(0.7f, 0.7f));
    }

}
