package com.example.bianney.myapplication.others;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Bianney on 29/07/2016.
 */
public class Monuments implements IMonuments{

    private static Monuments instance;
    private List<Historical> historical;
    private List<Natural> natural;

    // El constructor privado no permite que se genere un constructor por defecto.
    // (con mismo modificador de acceso que la definición de la clase)
    private Monuments() {
        historical = new ArrayList<Historical>();
        natural = new ArrayList<Natural>();
    }

    public static Monuments getInstance() {
        if (instance == null){
            instance  = new Monuments();
        }
        return instance;
    }

    public void addHistorical(Historical monument){
        historical.add(monument);
    }

    public void addNatural(Natural monument){
        natural.add(monument);
    }

    public List<Historical> getHistorical(){
        return historical;
    }

    public List<Natural> getNatural(){
        return natural;
    }

    public void print(){
        for (int i = 0; i < Monuments.getInstance().getHistorical().size(); i++){
            Log.d("TFGApplication", "Histórico nº" + i + ": " + Monuments.getInstance().getHistorical().get(i).getName() + " LONGITUD: " + Monuments.getInstance().getHistorical().get(i).getLongitude() + " LATITUD: " + Monuments.getInstance().getHistorical().get(i).getLatitude());
        }
        for (int i = 0; i < Monuments.getInstance().getNatural().size(); i++){
            Log.d("TFGApplication", "Natural nº" + i + ": " + Monuments.getInstance().getNatural().get(i).getName());
        }
    }

    public List<String> getHistoricalNames(){
        List<String> names = new ArrayList<String>(historical.size());
        for (int i = 0; i < historical.size(); i++){
            names.add(historical.get(i).getName());
        }
        return names;
    }

    public List<String> getNaturalNames(){
        List<String> names = new ArrayList<String>(natural.size());
        for (int i = 0; i < natural.size(); i++){
            names.add(natural.get(i).getName());
        }
        return names;
    }

    public int getPositionByName (String name){
        for (int i = 0; i < historical.size(); i++){
            if (name.equals(historical.get(i).getName())){
                return i;
            }
        }
        for (int i = 0; i < natural.size(); i++){
            if (name.equals(natural.get(i).getName())){
                return i;
            }
        }
        return -1;
    }

    public int getTypeByName (String name){
        for (int i = 0; i < historical.size(); i++){
            if (name.equals(historical.get(i).getName())){
                return 1;
            }
        }
        for (int i = 0; i < natural.size(); i++){
            if (name.equals(natural.get(i).getName())){
                return 2;
            }
        }
        return -1;
    }
}
