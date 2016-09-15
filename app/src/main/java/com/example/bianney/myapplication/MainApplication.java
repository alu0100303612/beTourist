package com.example.bianney.myapplication;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.bianney.myapplication.others.Historical;
import com.example.bianney.myapplication.others.Monument;
import com.example.bianney.myapplication.others.Monuments;
import com.example.bianney.myapplication.others.Natural;
import com.kontakt.sdk.android.common.KontaktSDK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bianney on 17/08/2016.
 */
public class MainApplication extends Application {
    private List<Monument> arraySearch = new ArrayList();
    private static final String TFG = "TFGApplication";
    //---------------------------------------------------------------------------------------------


    /*------------------
     * ATTRIBUTES
     * -----------------*/
    private static MainApplication instance = new MainApplication();

    public MainApplication() {
        instance = this;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        KontaktSDK.initialize(this);
        try {
            read("historical.json");
            read("natural.json");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Monuments.getInstance().print();
    }

    public static Context getContext() {
        return instance;
    }

    public static MainApplication getInstance() {
        return instance;
    }
    public static void setInstance(MainApplication instance) {
        MainApplication.instance = instance;
    }

    public void read(String fileName) throws org.json.simple.parser.ParseException, JSONException {
        Log.d(TFG, fileName);

        JSONObject jsonObject = new JSONObject(loadJSONFromAsset(fileName));

        // get a String from the JSON object
        String title = (String) jsonObject.get("help");
        Log.d(TFG, title);

        // get an array from the JSON object
        JSONArray monuments = jsonObject.getJSONArray("listado");

        // take each value from the json array separately
        for (int i = 0; i < monuments.length(); i++){
            String name = monuments.getJSONObject(i).getString("ows_LinkTitle");
            String zone = monuments.getJSONObject(i).getString("ows_Zona");
            String address = monuments.getJSONObject(i).getString("ows_Direccion");
            String cp = monuments.getJSONObject(i).getString("ows_CodigoPostal");
            String town = monuments.getJSONObject(i).getString("ows_Municipio");
            String geoReference = monuments.getJSONObject(i).getString("ows_Georeferencia");
            double latitude;
            double longitude;
            if ("".equals(geoReference)) {
                latitude = -5;
                longitude = 8;
            } else {
                String[] parts = geoReference.split(",");
                String lat = parts[0].replace("(", "");
                latitude = Double.parseDouble(lat);
                String lon = parts[1].replace(" ", "");
                lon = parts[1].replace(")", "");
                longitude = Double.parseDouble(lon);
            }
            String phoneNumber = monuments.getJSONObject(i).getString("ows_Telefono");
            String web = monuments.getJSONObject(i).getString("ows_Web");
            String situation = monuments.getJSONObject(i).getString("ows_Situacion");
            String spanishDescription = monuments.getJSONObject(i).getString("ows_DescripcionEspanol");
            String englishDescription = monuments.getJSONObject(i).getString("ows_DescripcionIngles");
            String germanDescription = monuments.getJSONObject(i).getString("ows_DescripcionAleman");
            String update = monuments.getJSONObject(i).getString("ows_FechaActualizacion");
            String dificulty = monuments.getJSONObject(i).getString("ows_Dificultad");
            String danger = monuments.getJSONObject(i).getString("ows_Peligrosidad");

            if ("historical.json".equals(fileName)){
                Historical historical = new Historical(name, zone, address, cp, town, longitude, latitude, phoneNumber, web, situation, spanishDescription,
                        englishDescription, germanDescription, update, dificulty, danger,"historical2");
                Log.d(TFG, "nombre: " + name);
                Monuments.getInstance().addHistorical(historical);
            } else if ("natural.json".equals(fileName)){
                Natural natural = new Natural(name, zone, address, cp, town, longitude, latitude, phoneNumber, web, situation, spanishDescription,
                        englishDescription, germanDescription, update, dificulty, danger, "natural2");
                Log.d(TFG, "nombre: " + natural.getName());
                Monuments.getInstance().addNatural(natural);
            }
        }
    }

    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public void updateSearchArray(String wordToSearch) {
        int size = Monuments.getInstance().getHistorical().size();
        arraySearch.clear();
        if (size < Monuments.getInstance().getNatural().size()) {
            size = Monuments.getInstance().getNatural().size();
        }
        for (int i = 0; i < size; i++) {
            try {
                if (( i < Monuments.getInstance().getHistorical().size())&&(Monuments.getInstance().getHistorical().get(i).getName().toLowerCase().contains(wordToSearch.toLowerCase()))) {
                    arraySearch.add(Monuments.getInstance().getHistorical().get(i));
                }else if (( i < Monuments.getInstance().getHistorical().size())&&(Monuments.getInstance().getHistorical().get(i).getTown().toLowerCase().contains(wordToSearch.toLowerCase()))){
                    arraySearch.add(Monuments.getInstance().getHistorical().get(i));
                }
                if ((i < Monuments.getInstance().getNatural().size())&&(Monuments.getInstance().getNatural().get(i).getName().toLowerCase().contains(wordToSearch.toLowerCase()))) {
                    arraySearch.add(Monuments.getInstance().getNatural().get(i));
                }else if(( i < Monuments.getInstance().getHistorical().size())&&(Monuments.getInstance().getNatural().get(i).getTown().toLowerCase().contains(wordToSearch.toLowerCase()))){
                    arraySearch.add(Monuments.getInstance().getNatural().get(i));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Monument> getArraySearch() {
        return arraySearch;
    }
    public Object getObjectSearch(int index) {
        return arraySearch.get(index);
    }
    public void setArraySearch(List<Monument> arraySearch) {
        this.arraySearch = arraySearch;
    }
}