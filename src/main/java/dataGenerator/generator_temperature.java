package dataGenerator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.ceil;

public class generator_temperature extends generator{
    List<List<String>> partList=new ArrayList<>();
    int partIndex;
    public generator_temperature() {
        partList.add(Arrays.asList("temperature1_high","temperature1_normal"));
        partList.add(Arrays.asList("temperature2_high","temperature2_normal"));
        partList.add(Arrays.asList("temperature3_normal1","temperature3_normal2"));

        Timestamp clock=new Timestamp(System.currentTimeMillis());
        initialTime=clock.getTime();
        previousTime=initialTime;
        int hour = clock.toLocalDateTime().getHour();
        int min = clock.toLocalDateTime().getMinute();
        int sec = clock.toLocalDateTime().getSecond();
        if(hour<8){
            partIndex=0;
            Former=data.loadFile(fileSelector());
            Former=Former.subList(3600*hour+60*min+sec,Former.size()-1);
            partIndex++;
            Latter=data.loadFile(fileSelector());
        } else if (hour<16) {
            partIndex=1;
            Former=data.loadFile(fileSelector());
            Former=Former.subList(3600*(hour-8)+60*min+sec,Former.size()-1);
            partIndex++;
            Latter=data.loadFile(fileSelector());
        }else {
            partIndex=2;
            Former=data.loadFile(fileSelector());
            Former=Former.subList(3600*(hour-16)+60*min+sec,Former.size()-1);
            partIndex=0;
            Latter=data.loadFile(fileSelector());
        }
        size= Former.size();
        Former.addAll(Latter);
    }
    public String fileSelector() {
        super.fileSelector();
        if (fileIndex<50) return partList.get(partIndex).get(0);
        else return partList.get(partIndex).get(1);
    }
    @Override
    public List<Double> outputValues(int fs){
        if (index2>size-1){
            partIndex++;
            if (partIndex==3){
                partIndex=0;
            }
            initialTime=initialTime+(1000/fs)*size;
            Former.subList(0,size-1).clear();
            size= Former.size();
            Latter =data.loadFile(fileSelector());
            Former.addAll(Latter);
        }
        long currentTime=new Timestamp(System.currentTimeMillis()).getTime();
        index1= (int) ceil((((Long)previousTime)-((Long)initialTime))/(1000/fs));
        index2= (int) ceil((((Long)currentTime)-((Long)initialTime))/(1000/fs));
        previousTime=currentTime;
        return Former.subList(index1,index2);
    }
}
