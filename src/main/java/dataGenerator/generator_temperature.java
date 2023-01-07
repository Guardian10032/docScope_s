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

    public generator_temperature(long initialTime) {
        super(initialTime);
        partList.add(Arrays.asList("temperature1_high","temperature1_normal"));
        partList.add(Arrays.asList("temperature2_high","temperature2_normal"));
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
        fileIndex=rand.nextInt(99);
        if (fileIndex<50) return partList.get(partIndex).get(0);
        else return partList.get(partIndex).get(1);
    }

    public List<Double> outputValues(long currentTime){
        if (index2>size-1){
            partIndex++;
            if (partIndex==3){
                partIndex=0;
            }
            initialTime=initialTime+1000*size;
            Former.subList(0,size-1).clear();
            size= Former.size();
            Latter =data.loadFile(fileSelector());
            Former.addAll(Latter);
        }
        index1= (int) ceil((previousTime-initialTime)/1000);
        index2= (int) ceil((currentTime-initialTime)/1000);
        previousTime=currentTime;
        return Former.subList(index1,index2);
    }
}
