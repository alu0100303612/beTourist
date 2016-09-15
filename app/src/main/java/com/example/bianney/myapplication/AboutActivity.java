package com.example.bianney.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView addressTxt = (TextView) findViewById(R.id.txt_title);
        Typeface face1= Typeface.createFromAsset(getAssets(), "fonts/Street Gathering.ttf");
        addressTxt.setTypeface(face1);
        ImageView aboutImgContact = (ImageView) findViewById(R.id.img_contact);
        aboutImgContact.setImageResource(R.drawable.ic_mail_guay);

    }
}
