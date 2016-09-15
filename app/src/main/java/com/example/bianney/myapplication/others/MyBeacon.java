package com.example.bianney.myapplication.others;

/**
 * Created by Bianney on 30/08/2016.
 */
public class MyBeacon {
    String name;
    String password;
    int type;
    int position;
    boolean viewed;

    public  MyBeacon (String name, String password, int type, int position, boolean viewed){
        this.name = name;
        this.password = password;
        this.type = type;
        this.position = position;
        this.viewed = viewed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean getViewed(){
        return viewed;
    }

    public void setViewed(boolean viewed){
        this.viewed = viewed;
    }
}
