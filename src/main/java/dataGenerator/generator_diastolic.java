package dataGenerator;

import java.util.List;
import java.util.Random;

import static java.lang.Math.ceil;

public class generator_diastolic extends generator{
    public generator_diastolic(long initialTime) {
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
        if (fileIndex<10) return "bloodlow_high1";
        else if (fileIndex<20) return "bloodlow_high2";
        else if (fileIndex<30) return "bloodlow_low1";
        else if (fileIndex<40) return "bloodlow_low2";
        else if (fileIndex<50) return "bloodlow_normal1";
        else if (fileIndex<60) return "bloodlow_normal2";
        else if (fileIndex<70) return "bloodlow_normal3";
        else return "bloodlow_normal4";
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
