package Servlet;

import java.util.*;

public class dataBase {

    private static TreeMap<Long,List<Double>> data_ecg1=new TreeMap<>();
    private static TreeMap<Long,List<Double>> data_temperature=new TreeMap<>();
    public void addData_ecg1(long time,List<Double> ecg1){
        this.data_ecg1.put(time,ecg1);
    }
    public List<Double> getData_ecg1(long timestamp){
        return data_ecg1.get(timestamp);
    }
    public NavigableSet getEcg1TimeSet(){
        return data_ecg1.descendingKeySet();
    }
    public void addData_temperature(long time,List<Double> ecg1){
        this.data_temperature.put(time,ecg1);
    }
    public List<Double> getData_temperature(long timestamp){
        return data_temperature.get(timestamp);
    }
    public NavigableSet getTemperatureTimeSet(){
        return data_temperature.descendingKeySet();
    }
}
