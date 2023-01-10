package dataGenerator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Math.ceil;

public class generator_temperature extends generator{
    List<List<String>> partList=new ArrayList<>();
    int partIndex;

    public generator_temperature(long initialTime,String status) {
        super(initialTime,status);
        interval=1000;
        partList.add(Arrays.asList("temperature1_normal","temperature1_high"));
        partList.add(Arrays.asList("temperature2_normal","temperature2_high"));
        partList.add(Arrays.asList("temperature3_normal1","temperature3_normal2"));

        Timestamp clock=new Timestamp(initialTime);
        previousTime=initialTime;
        int hour = clock.toLocalDateTime().getHour();
        int min = clock.toLocalDateTime().getMinute();
        int sec = clock.toLocalDateTime().getSecond();
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
        if (fileIndex<50) return partList.get(partIndex).get(0);
        else return partList.get(partIndex).get(1);
    }
}
