package netRelated;

import java.util.List;

public class requestPack{
    public long startTime;
    public long endTime;
    public List<Short> type;
    // 1:ecg1, 2:ecg2, 3:RESP
    // 4:body temperature, 5:heart rate, 6:systolic pressure, 7:diastolic pressure, 8:respiratory rate
    public requestPack(List<Short> type, long startTime, long endTime){
        this.type=type;
        this.startTime=startTime;
        this.endTime=endTime;
    }
}
