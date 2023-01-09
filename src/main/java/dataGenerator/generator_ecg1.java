package dataGenerator;

import java.sql.Timestamp;

import java.util.List;
import java.util.Random;

import static java.lang.Math.ceil;

public class generator_ecg1 extends generatorHeartRelate{
    public generator_ecg1(long initialTime) {
        super(initialTime);
        previousTime=initialTime;
        Former =data.loadFile(fileSelector());
        Latter =data.loadFile(fileSelector());
        size= Former.size();
        Former.addAll(Latter);
    }
    public String fileSelector() {
        randomHeartGenerator();
        if (fileHeartIndex<40) return "ecg1_normal1";
        else if (fileHeartIndex<80) return "ecg1_normal2";
        else if (fileHeartIndex<90) return "ecg1_high";
        else return "ecg1_low";
    }
    public List<Double> outputValues(long currentTime){
        if (index2>size-1){
            initialTime=initialTime+2*size;
            Former.subList(0,size-1).clear();
            size= Former.size();
            Latter =data.loadFile(fileSelector());
            Former.addAll(Latter);
        }
        index1= (int) ceil((previousTime-initialTime)/2);
        index2= (int) ceil((currentTime-initialTime)/2);
        previousTime=currentTime;
        return Former.subList(index1,index2);
    }

}
