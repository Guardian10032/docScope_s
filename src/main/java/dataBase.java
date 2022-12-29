import java.util.*;

public class dataBase {

    private static long timestamp;
    private static TreeMap<Long,List<Double>> data_ecg1=new TreeMap<>();
    public void setTimestamp(long timestamp){
        this.timestamp=timestamp;
    }
    public long getTimestamp(){
        return timestamp;
    }
    public void addData_ecg1(long time,List<Double> ecg1){
        this.data_ecg1.put(time,ecg1);
    }
    public List<Double> getData_ecg1(long timestamp){
        return data_ecg1.get(timestamp);
    }
    public NavigableSet getTimeSet(){
        return data_ecg1.descendingKeySet();
    }


//    private static long timestamp;
//    private static List<List<Double>> data_ecg1=new ArrayList<>();
//    public void setTimestamp(long timestamp){
//        this.timestamp=timestamp;
//    }
//    public long getTimestamp(){
//        return timestamp;
//    }
//    public void addData_ecg1(List<Double> data_ecg1){
//        this.data_ecg1.add(data_ecg1);
//    }
//    public List<List<Double>> getData_ecg1(){
//        return data_ecg1;
//    }
//
//    public int getSize(){
//        return data_ecg1.size();
//    }
}
