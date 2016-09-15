package com.example.bianney.myapplication.others;

/**
 * Created by Bianney on 12/08/2016.
 */
public class Monument implements IMonument{

    private String name;
    private String zone;
    private String address;
    private String cp;
    private String town;
    private double longitude;
    private double latitude;
    private String phoneNumber;
    private String web;
    private String situation;
    private String spanishDescription;
    private String englishDescription;
    private String germanDescription;
    private String update;
    private String dificulty;
    private String danger;
    private String image;

    public Monument (){
    }

    public Monument (String name, String zone, String address, String cp, String town, double longitude, double latitude, String phoneNumber, String web,
                    String situation, String spanishDescription, String englishDescription, String germanDescription, String update, String dificulty,
                    String danger, String image){
        this.name = name;
        this.zone = zone;
        this.address = address;
        this.cp = cp;
        this.town = town;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phoneNumber = phoneNumber;
        this.web = web;
        this.situation = situation;
        this.spanishDescription = spanishDescription;
        this.englishDescription = englishDescription;
        this.germanDescription = germanDescription;
        this.dificulty = dificulty;
        this.update = update;
        this.danger = danger;
        this.image = image;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getZone() {
        return zone;
    }

    @Override
    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getCp() {
        return cp;
    }

    @Override
    public void setCp(String cp) {
        this.cp = cp;
    }

    @Override
    public String getTown() {
        return town;
    }

    @Override
    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getWeb() {
        return web;
    }

    @Override
    public void setWeb(String web) {
        this.web = web;
    }

    @Override
    public String getSituation() {
        return situation;
    }

    @Override
    public void setSituation(String situation) {
        this.situation = situation;
    }

    @Override
    public String getSpanishDescription() {
        return spanishDescription;
    }

    @Override
    public void setSpanishDescription(String spanishDescription) {
        this.spanishDescription = spanishDescription;
    }

    @Override
    public String getEnglishDescription() {
        return englishDescription;
    }

    @Override
    public void setEnglishDescription(String englishDescription) {
        this.englishDescription = englishDescription;
    }

    @Override
    public String getGermanDescription() {
        return germanDescription;
    }

    @Override
    public void setGermanDescription(String germanDescription) {
        this.germanDescription = germanDescription;
    }

    @Override
    public String getDificulty() {
        return dificulty;
    }

    @Override
    public void setDificulty(String dificulty) {
        this.dificulty = dificulty;
    }

    @Override
    public String getUpdate() {
        return update;
    }

    @Override
    public void setUpdate(String update) {
        this.update = update;
    }

    @Override
    public String getDanger() {
        return danger;
    }

    @Override
    public void setDanger(String danger) {
        this.danger = danger;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }
}
