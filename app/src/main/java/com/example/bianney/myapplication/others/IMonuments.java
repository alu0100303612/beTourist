package com.example.bianney.myapplication.others;

import java.util.List;

/**
 * Created by Bianney on 12/08/2016.
 */
public interface IMonuments {
    void addHistorical(Historical monument);
    void addNatural(Natural monument);
    List<Historical> getHistorical();
    List<Natural> getNatural();
    void print();
    List<String> getHistoricalNames();
    List<String> getNaturalNames();
}
