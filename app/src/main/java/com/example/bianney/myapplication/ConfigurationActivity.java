package com.example.bianney.myapplication;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ConfigurationActivity extends FragmentActivity {
    private static final String DISABLE = "disable";
    private static final String ENABLE = "enable";
    private static final String ARTINTEREST = "artInterest";
    private static final String SEAINTEREST = "seaIinterest";
    private static final String HISTORYINTEREST = "historyInterest";
    private static final String LOOKOUTINTEREST = "lookoutInterest";
    private static final String MOUNTAININTEREST = "mountainInterest";
    private static final String RELIGIONINTEREST = "religionInterest";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        Typeface face1= Typeface.createFromAsset(getAssets(), "fonts/Street Gathering.ttf");
        TextView addressTxt = (TextView) findViewById(R.id.txt_title);
        addressTxt.setTypeface(face1);

        final ImageView artInterestImg = (ImageView) findViewById(R.id.interes_arte);
        final ImageView seaInterestImg = (ImageView) findViewById(R.id.interes_agua);
        final ImageView historyInterestImg = (ImageView) findViewById(R.id.interes_historia);
        final ImageView viewPointInterestImg = (ImageView) findViewById(R.id.interes_mirador);
        final ImageView mountainInterestImg = (ImageView) findViewById(R.id.interes_montana);
        final ImageView religionInterestImg = (ImageView) findViewById(R.id.interes_religion);


        //Write data from user
        final SharedPreferences.Editor editor = getSharedPreferences("BeTouristAppUserPreference", MODE_PRIVATE).edit();
        //Read data from user
        final SharedPreferences prefs = getSharedPreferences("BeTouristAppUserPreference", MODE_PRIVATE);
        //Load State from user
        String name = prefs.getString(ARTINTEREST, DISABLE);//"disable" is the default value.
        setImage(name, artInterestImg, R.drawable.interes_arte_disable, R.drawable.interes_arte);

        String name2 = prefs.getString(SEAINTEREST, DISABLE);//"disable" is the default value.
        setImage(name2, seaInterestImg, R.drawable.interes_agua_disable, R.drawable.interes_agua);

        String name3 = prefs.getString(HISTORYINTEREST, DISABLE);//"disable" is the default value.
        setImage(name3, historyInterestImg, R.drawable.interes_historia_disable, R.drawable.interes_historia);

        String name4 = prefs.getString(LOOKOUTINTEREST, DISABLE);//"disable" is the default value.
        setImage(name4, viewPointInterestImg, R.drawable.interes_mirador_disable, R.drawable.interes_mirador);

        String name5 = prefs.getString(MOUNTAININTEREST, DISABLE);//"disable" is the default value.
        setImage(name5, mountainInterestImg, R.drawable.interes_montana_disable, R.drawable.interes_montana);

        String name6 = prefs.getString(RELIGIONINTEREST, DISABLE);//"disable" is the default value.
        setImage(name6, religionInterestImg, R.drawable.interes_religion_disable, R.drawable.interes_religion);


        artInterestImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = prefs.getString(ARTINTEREST, DISABLE);//"disable" is the default value.
                changeImage(name, ARTINTEREST, editor, artInterestImg, R.drawable.interes_arte_disable, R.drawable.interes_arte);
            }
        });

        seaInterestImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = prefs.getString(SEAINTEREST, DISABLE);//"disable" is the default value.
                changeImage(name, SEAINTEREST, editor, seaInterestImg, R.drawable.interes_agua_disable, R.drawable.interes_agua);
            }
        });

        historyInterestImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = prefs.getString(HISTORYINTEREST, DISABLE);//"disable" is the default value.
                changeImage(name, HISTORYINTEREST, editor, historyInterestImg, R.drawable.interes_historia_disable, R.drawable.interes_historia);
            }
        });

        viewPointInterestImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = prefs.getString(LOOKOUTINTEREST, DISABLE);//"disable" is the default value.
                changeImage(name, LOOKOUTINTEREST, editor, viewPointInterestImg, R.drawable.interes_mirador_disable, R.drawable.interes_mirador);
            }
        });

        mountainInterestImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = prefs.getString(MOUNTAININTEREST, DISABLE);//"disable" is the default value.
                changeImage(name, MOUNTAININTEREST, editor, mountainInterestImg, R.drawable.interes_montana_disable, R.drawable.interes_montana);
            }
        });

        religionInterestImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = prefs.getString(RELIGIONINTEREST, DISABLE);//"disable" is the default value.
                changeImage(name, RELIGIONINTEREST, editor, religionInterestImg, R.drawable.interes_religion_disable, R.drawable.interes_religion);
            }
        });


    }

    public void setImage(String name, ImageView image, int imageNumber, int image2Number){
        if (!name.equals(ENABLE)){
            image.setImageResource(imageNumber);
        }else{
            image.setImageResource(image2Number);
        }
    }

    public void changeImage(String name, String word, SharedPreferences.Editor editor, ImageView image, int imageNumber, int image2Number){
        if (name.equals(ENABLE)){
            editor.putString(word, DISABLE);
            editor.commit();
            image.setImageResource(imageNumber);
        }
        else{
            editor.putString(word, ENABLE);
            editor.commit();
            image.setImageResource(image2Number);
        }
    }

}
