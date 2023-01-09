package dataGenerator;

import java.util.List;

import static java.lang.Math.ceil;

public class generator_heartRate extends generatorHeartRelate{
    public generator_heartRate(long initialTime) {
        super(initialTime);
        previousTime=initialTime;
        Former =data.loadFile(fileSelector());
        Latter =data.loadFile(fileSelector());
        size= Former.size();
        Former.addAll(Latter);
    }
    public String fileSelector() {
        randomHeartGenerator();
        if (fileHeartIndex<40) return "heart_normal1";
        else if (fileHeartIndex<80) return "heart_normal2";
        else if (fileHeartIndex<90) return "heart_high";
        else return "heart_low";
    }

    public List<Double> outputValues(long currentTime){
        if (index2>size-1){
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
