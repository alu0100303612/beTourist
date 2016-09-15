package com.example.bianney.myapplication.others;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bianney on 30/08/2016.
 */
public class MyBeacons {
    private static MyBeacons instance;
    private List<MyBeacon> beaconsList = new ArrayList<MyBeacon>();

    private MyBeacons(){
        MyBeacon historicalBeacon = new MyBeacon("Historical", "hJUi", 0, 2, false);
        MyBeacon naturalBeacon = new MyBeacon("Natural","A23d", 1, 2, false);
        beaconsList.add(historicalBeacon);
        beaconsList.add(naturalBeacon);
    }

    public static MyBeacons getInstance() {
        if (instance == null){
            instance  = new MyBeacons();
        }
        return instance;
    }

    public List<MyBeacon> getList(){
        return beaconsList;
    }
}
