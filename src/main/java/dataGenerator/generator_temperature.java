package dataGenerator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * a simulation of the machine measuring body temperature
 */
public class generator_temperature extends generator{
    List<List<String>> partList=new ArrayList<>();
    /**
     * 0 means 0am-8am; 1 means 8am-4pm; 2 means 4pm-12pm
     */
    int partIndex;
    /**
     * load two files based on status and current time, because body temperature is different across the day
     * @param initialTime save the initial time when the object is created,
     *                    this is also the corresponding time of the first value
     * @param status normal or abnormal
     */
    public generator_temperature(long initialTime,String status) {
        super(initialTime,status);
        interval=1000;
        partList.add(Arrays.asList("temperature1_normal","temperature1_high"));//0am-8am
        partList.add(Arrays.asList("temperature2_normal","temperature2_high"));//8am-4pm
        partList.add(Arrays.asList("temperature3_normal1","temperature3_normal2"));//4pm-12pm
        //get current time
        Timestamp clock=new Timestamp(initialTime);
        previousTime=initialTime;
        int hour = clock.toLocalDateTime().getHour();
        int min = clock.toLocalDateTime().getMinute();
        int sec = clock.toLocalDateTime().getSecond();
        //get corresponding value
        if(hour<8){
            partIndex=0;
            Former=data.loadFile(fileSelector());
            Former=new ArrayList<>(Former.subList(3600*hour+60*min+sec,Former.size()-1));
            partIndex++;
            Latter=data.loadFile(fileSelector());
        } else if (hour<16) {
            partIndex=1;
            Former=data.loadFile(fileSelector());
            Former=new ArrayList<>(Former.subList(3600*(hour-8)+60*min+sec,Former.size()-1));
            partIndex++;
            Latter=data.loadFile(fileSelector());
        }else {
            partIndex=2;
            Former=data.loadFile(fileSelector());
            Former=new ArrayList<>(Former.subList(3600*(hour-16)+60*min+sec,Former.size()-1));
            partIndex=0;
            Latter=data.loadFile(fileSelector());
        }
        size= Former.size();
        Former.addAll(Latter);
    }
    public String fileSelector() {
        Random rand = new Random();
        fileIndex=rand.nextInt(randomLimit);
        int temp=partIndex;
        partIndex++;
        if (partIndex==3) partIndex=0;
        if (fileIndex<50) {
            return partList.get(temp).get(0);//normal one
        }
        else {
            return partList.get(temp).get(1);//abnormal one
        }
    }
}
