package dataGenerator;

import java.util.List;

import static java.lang.Math.ceil;

public class generator_respiratoryRate extends generatorRespiratoryRelate{
    public generator_respiratoryRate(long initialTime) {
        super(initialTime);
        previousTime=initialTime;
        Former =data.loadFile(fileSelector());
        Latter =data.loadFile(fileSelector());
        size= Former.size();
        Former.addAll(Latter);
    }
    public String fileSelector() {
        randomRespiratoryGenerator();
        if (fileRespiratoryIndex<40) return "respiratory_normal1";
        else if (fileRespiratoryIndex<80) return "respiratory_normal2";
        else if (fileRespiratoryIndex<90) return "respiratory_high";
        else return "respiratory_low";
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
