package dataGenerator;

import java.util.List;

import static java.lang.Math.ceil;

public class generator_ecg2 extends generatorHeartRelate{
    public generator_ecg2(long initialTime) {
        super(initialTime);
        previousTime=initialTime;
        Former =data.loadFile(fileSelector());
        Latter =data.loadFile(fileSelector());
        size= Former.size();
        Former.addAll(Latter);
    }
    public String fileSelector() {
        randomHeartGenerator();
        if (fileHeartIndex<40) return "ecg2_normal1";
        else if (fileHeartIndex<80) return "ecg2_normal2";
        else if (fileHeartIndex<90) return "ecg2_high";
        else return "ecg2_low";
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
