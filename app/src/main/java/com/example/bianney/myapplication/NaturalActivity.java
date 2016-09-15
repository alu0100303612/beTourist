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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bianney.myapplication.others.Monuments;
import com.example.bianney.myapplication.others.Natural;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class NaturalActivity  extends FragmentActivity implements OnMapReadyCallback {
    //http://www.flaticon.com/
    Natural natural = null;
    private static final double START_LAT = 28.2936267;
    private static final double START_LONG = -16.5300522;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_natural);
        TextView nameTxt = (TextView) findViewById(R.id.txt_name);
        TextView townTxt = (TextView) findViewById(R.id.txt_town_two);
        TextView addressTxt = (TextView) findViewById(R.id.txt_address_two);
        TextView descriptionTxt = (TextView) findViewById(R.id.txt_description_two);
        TextView difficultyTxt = (TextView) findViewById(R.id.txt_difficulty_two);
        TextView dangerousTxt = (TextView) findViewById(R.id.txt_dangerous_two);
        ImageView monumentImg = (ImageView) findViewById(R.id.img_monument);
        ImageView monumentImgDescrp = (ImageView) findViewById(R.id.img_description);
        ImageView monumentImgTown = (ImageView) findViewById(R.id.img_town);
        ImageView monumentImgAddre = (ImageView) findViewById(R.id.img_address);
        ImageView monumentImgInfr = (ImageView) findViewById(R.id.img_difficulty);
        ImageView monumentImgInfr2 = (ImageView) findViewById(R.id.img_dangerous);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        try{
            Bundle extras = getIntent().getExtras();
            int position = extras.getInt("mPosition");
            natural = (Natural) Monuments.getInstance().getNatural().get(position);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try{
            int imageResource = getResources().getIdentifier(natural.getImage()+"big", "drawable", getPackageName());
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
            monumentImgInfr2.setImageResource(R.drawable.ic_information_verde_azul);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        try{
            Typeface face1= Typeface.createFromAsset(getAssets(), "fonts/Street Gathering.ttf");
            nameTxt.setTypeface(face1);
            nameTxt.setText(natural.getName());
            townTxt.setText(natural.getTown());
            addressTxt.setText(natural.getAddress());
            difficultyTxt.setText(natural.getDificulty());
            dangerousTxt.setText(natural.getDanger());
            if ("español".equals(Locale.getDefault().getDisplayLanguage())) {
                descriptionTxt.setText(natural.getSpanishDescription());
            } else if ("English".equals(Locale.getDefault().getDisplayLanguage())){
                descriptionTxt.setText(natural.getEnglishDescription());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        LatLng tenerife = new LatLng(START_LAT, START_LONG);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tenerife));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(9));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng monument = new LatLng (natural.getLatitude(), natural.getLongitude());
        Drawable circle = getResources().getDrawable(R.drawable.ic_mountain_violet);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(circle.getIntrinsicWidth(), circle.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        circle.setBounds(0, 0, circle.getIntrinsicWidth(), circle.getIntrinsicHeight());
        circle.draw(canvas);
        BitmapDescriptor bd = BitmapDescriptorFactory.fromBitmap(bitmap);
        mMap.addMarker(new MarkerOptions()
                .position(monument)
                .title(natural.getName())
                .snippet(natural.getAddress())
                .icon(bd)
                .anchor(0.7f, 0.7f));
    }
}