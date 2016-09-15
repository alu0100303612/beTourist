package com.example.bianney.myapplication.others;

/**
 * Created by Bianney on 28/07/2016.
 */
public class Natural extends Monument{

    public Natural (){
        super();
    }

    public Natural (String name, String zone, String address, String cp, String town, double longitude, double latitude, String phoneNumber, String web,
                       String situation, String spanishDescription, String englishDescription, String germanDescription, String update, String dificulty,
                       String danger, String image){
        super (name, zone, address, cp, town, longitude, latitude, phoneNumber, web, situation, spanishDescription, englishDescription,
                germanDescription, update, dificulty, danger, image);
    }
}
