package com.example.bianney.myapplication;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bianney.myapplication.others.Historical;
import com.example.bianney.myapplication.others.Monuments;

/**
 * Created by Bianney on 30/08/2016.
 */
public class DialogMessage extends DialogFragment {
    int position;
    SharedPreferences prefs;
    private static final String ENABLE = "enable";
    private static final String DISABLE = "disable";
    private static final String ARTINTEREST = "artInterest";
    private static final String HISTORYINTEREST = "historyInterest";
    private static final String RELIGIONINTEREST = "religionInterest";
    private static final String ERROR = "Error";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Log.d("Beacon", "LLEGA1 DIALOG);");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        dialogBuilder.setView(dialogView);

        TextView nameTxt = (TextView) dialogView.findViewById(R.id.txt_name);
        TextView descriptionTxt = (TextView) dialogView.findViewById(R.id.txt_description);
        TextView descriptionTwoTxt = (TextView) dialogView.findViewById(R.id.txt_description_two);
        TextView descriptionThreeTxt = (TextView) dialogView.findViewById(R.id.txt_description_three);
        ImageView monumentImg = (ImageView) dialogView.findViewById(R.id.img_monument);
        ImageView descriptionImg = (ImageView) dialogView.findViewById(R.id.img_description);
        ImageView description2Img = (ImageView) dialogView.findViewById(R.id.img_description_two);
        ImageView description3Img = (ImageView) dialogView.findViewById(R.id.img_description_three);

        setTitle(nameTxt);
        setImages(monumentImg);
        setDescriptions(descriptionTxt, descriptionTwoTxt, descriptionThreeTxt, descriptionImg, description2Img, description3Img);
        // Create the AlertDialog object and return it
        return dialogBuilder.create();
    }

    public void setPosition (int position){
        //0: Historical, 1: Natural
        this.position = position;
    }

    public void setPrefs(SharedPreferences prefs){
        this.prefs = prefs;
    }

    public void setDescriptions(TextView descriptionTxt, TextView descriptionTwoTxt, TextView descriptionThreeTxt, ImageView descriptionImg, ImageView description2Img, ImageView description3Img){

        try {
            String name = prefs.getString(HISTORYINTEREST, DISABLE);//"disable" is the default value.
            Log.d(ERROR, "DialogMessage name: "+name);
            if (ENABLE.equals(name)){
                descriptionTxt.setText("Este edificio fue declarado Bien de Interés Cultural y  en el año 2011,  elevado a dignidad de Basílica Menor  por el Papa Benedicto XVI. " +
                        "Su construcción finalizó en 1959 e implicó a la sociedad isleña, la cual no dudó en realizar donaciones para sufragar sus costes. El Obispo Domingo Pérez" +
                        " Cáceres promovió la obra y se la encargó al arquitecto tinerfeño  Enrique Marrero Regalado.");
                descriptionImg.setImageResource(R.drawable.interes_historia);
            }

            String name2 = prefs.getString(RELIGIONINTEREST, DISABLE);//"disable" is the default value.
            if (ENABLE.equals(name2)){
                descriptionTwoTxt.setText("La Basílica está consagrada a la Virgen de Candelaria, Patrona General del Archipiélago Canario. Hoy vemos  a la Virgen situada dentro " +
                        "de un baldaquino de madera, decorado con motivos vegetales dorados y rodeada de angelotes. A los pies de la imagen aparece la luna en cuarto creciente o " +
                        "media luna, que hace alusión al pasaje  del Apocalipsis, versículo 12: “apareció en el cielo una señal grande, una mujer vestida de sol, con la luna a los" +
                        " pies”.");
                description2Img.setImageResource(R.drawable.interes_religion);
            }

            String name3 = prefs.getString(ARTINTEREST, DISABLE);//"disable" is the default value.
            if (ENABLE.equals(name3)){
                descriptionThreeTxt.setText("Como resultado, vemos hoy un edificio moderno, de estilo neo-canario: una mezcla ecléctica de los diferentes  estilos constructivos que" +
                        " se han dado en Canarias. Los capiteles de la nave central son de orden dórico, y la policromía de la techumbre alude de modo simbólico, a los tonos de la" +
                        " vestimenta clásica de la Virgen. El Altar Mayor está decorado  con un inmenso mural del pintor José Aguiar y alberga la actual Imagen de La Candelaria," +
                        " obra del escultor canario Fernando Estévez del Sacramento. A  él se le encargó esta delicada obra tras desaparecer  en el aluvión de 1826 la primitiva " +
                        "imagen hallada por los guanches.");
                description3Img.setImageResource(R.drawable.interes_arte);
            }

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setImages(ImageView monumentImg){

        Historical historical = (Historical) Monuments.getInstance().getHistorical().get(2);
        try{
            Log.d(ERROR, "nombre de la image");
            Log.d(ERROR, "nombre de la image: "+ historical.getImage());
            String imageName = historical.getImage()+"big";
            int imageResource = getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
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

    }

    public void setTitle(TextView nameTxt){
        try{
            Historical historical = (Historical) Monuments.getInstance().getHistorical().get(2);
            Typeface face1= Typeface.createFromAsset(getActivity().getAssets(), "fonts/Street Gathering.ttf");
            nameTxt.setTypeface(face1);
            nameTxt.setText(historical.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
