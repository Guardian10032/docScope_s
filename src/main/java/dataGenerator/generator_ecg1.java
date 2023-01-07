package dataGenerator;

import java.sql.Timestamp;

import java.util.List;
import java.util.Random;

import static java.lang.Math.ceil;

public class generator_ecg1 extends generator{
    int fs;
    public generator_ecg1(long initialTime) {
        super(initialTime);
        previousTime=initialTime;
        Former =data.loadFile(fileSelector());
        Latter =data.loadFile(fileSelector());
        size= Former.size();
        Former.addAll(Latter);
    }
    public String fileSelector() {
        Random rand = new Random();
        fileIndex=rand.nextInt(99);
        if (fileIndex<40) return "ecg1_normal1";
        else if (fileIndex<80) return "ecg1_normal2";
        else if (fileIndex<90) return "ecg1_high";
        else return "ecg1_low";
    }
    public List<Double> outputValues(){
        if (index2>size-1){
            initialTime=initialTime+2*size;
            Former.subList(0,size-1).clear();
            size= Former.size();
            Latter =data.loadFile(fileSelector());
            Former.addAll(Latter);
        }
        long currentTime=new Timestamp(System.currentTimeMillis()).getTime();
        index1= (int) ceil((previousTime-initialTime)/2);
        index2= (int) ceil((currentTime-initialTime)/2);
        previousTime=currentTime;
        return Former.subList(index1,index2);
    }

}
