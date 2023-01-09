package dataGenerator;

import java.util.List;
import java.util.Random;

import static java.lang.Math.ceil;

public class generator_resp extends generatorRespiratoryRelate{
    public generator_resp(long initialTime) {
        super(initialTime);
        previousTime=initialTime;
        Former =data.loadFile(fileSelector());
        Latter =data.loadFile(fileSelector());
        size= Former.size();
        Former.addAll(Latter);
    }
    public String fileSelector() {
        randomRespiratoryGenerator();
        if (fileRespiratoryIndex<40) return "resp_normal1";
        else if (fileRespiratoryIndex<80) return "resp_normal2";
        else if (fileRespiratoryIndex<90) return "resp_high";
        else return "resp_low";
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
