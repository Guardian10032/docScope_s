import java.util.ArrayList;
import java.util.List;

public class dataBase {
    private static long timestamp;
    private static List<Double> data_ecg1=new ArrayList<>();
    public void setTimestamp(long timestamp){
        this.timestamp=timestamp;
    }
    public long getTimestamp(){
        return timestamp;
    }
    public void addData_ecg1(List<Double> data_ecg1){
        this.data_ecg1.addAll(data_ecg1);
    }
    public List<Double> getData_ecg1(){
        return data_ecg1;
    }
}
